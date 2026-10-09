package com.example.staynest.profile.reactive.command.update_name;

import com.example.staynest.profile.reactive.query.get_encryption_data.GetEncryptionDataQueryHandler;
import io.github.kritin_puri_git.crypto.facade.CryptoFacade;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateNameCommandHandlerImpl implements UpdateNameCommandHandler{

    private final GetEncryptionDataQueryHandler queryHandler;
    private final CryptoFacade cryptoFacade;
    private final UpdateNameCommandRepository repository;
    public UpdateNameCommandHandlerImpl(
            GetEncryptionDataQueryHandler queryHandler,
            CryptoFacade cryptoFacade,
            UpdateNameCommandRepository repository
    ){
        this.queryHandler = queryHandler;
        this.cryptoFacade = cryptoFacade;
        this.repository = repository;
    }

    @Override
    public Mono<Void> handle(UpdateNameCommand command) {

        return this.queryHandler.handle(command.publicId())
                .switchIfEmpty(Mono.error(new IllegalStateException("Profile not found")))
                .flatMap(userProfile ->{

                    userProfile.setName(
                            this.cryptoFacade.encrypt(
                                    command.name(),
                                    userProfile.getEncryptionKeyId(),
                                    userProfile.getEncryptionVersion()
                            )
                    );

                    return this.repository.update(userProfile);
                });

    }
}
