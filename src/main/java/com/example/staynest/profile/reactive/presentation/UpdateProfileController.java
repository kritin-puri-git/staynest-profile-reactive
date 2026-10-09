package com.example.staynest.profile.reactive.presentation;

import com.example.staynest.profile.reactive.command.update_name.UpdateNameCommand;
import com.example.staynest.profile.reactive.command.update_name.UpdateNameCommandHandler;
import com.example.staynest.profile.reactive.presentation.dto.UpdateNameRequestDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/profile/update")
public class UpdateProfileController {

    private final UpdateNameCommandHandler updateNameCommandHandler;

    public UpdateProfileController(
            UpdateNameCommandHandler updateNameCommandHandler
    ){
        this.updateNameCommandHandler = updateNameCommandHandler;
    }

    @PatchMapping("/name")
    public Mono<Void> updateName(
            Authentication authentication,
            @RequestBody UpdateNameRequestDto requestDto
            ) {

        return Mono.defer(()->{
            String publicId = (String) authentication.getPrincipal();

            String name = requestDto.getName();

            UpdateNameCommand command = new UpdateNameCommand(publicId, name);

            return this.updateNameCommandHandler.handle(command);
        });
    }

    @PatchMapping("/bio")
    public void updateBio() {}

    @PatchMapping("/date-of-birth")
    public void updateDateOfBirth() {}

    @PatchMapping("/gender")
    public void updateGender() {}
}
