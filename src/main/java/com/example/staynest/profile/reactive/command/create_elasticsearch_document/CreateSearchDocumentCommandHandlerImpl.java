package com.example.staynest.profile.reactive.command.create_elasticsearch_document;

import com.example.staynest.profile.reactive.domain.SearchProfiles;
import io.github.kritin_puri_git.crypto.facade.CryptoFacade;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CreateSearchDocumentCommandHandlerImpl implements CreateSearchDocumentCommandHandler{

    private final CryptoFacade cryptoFacade;
    private final CreateSearchDocumentCommandRepository repository;

    public CreateSearchDocumentCommandHandlerImpl(
            CryptoFacade cryptoFacade,
            CreateSearchDocumentCommandRepository repository
    ){
        this.cryptoFacade = cryptoFacade;
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Void> handle(CreateSearchDocumentCommand command) {

        String plainPublicId = this.cryptoFacade.decrypt(
                command.publicId(),
                command.encryptionKeyId(),
                command.encryptionVersion()
        );
        String plainUsername = this.cryptoFacade.decrypt(
                command.username(),
                command.encryptionKeyId(),
                command.encryptionVersion()
        );

        SearchProfiles profiles = new SearchProfiles();

        profiles.setId(command.id());
        profiles.setPublicId(plainPublicId);
        profiles.setUsername(plainUsername);

        return this.repository.initiate(profiles);
    }
}
