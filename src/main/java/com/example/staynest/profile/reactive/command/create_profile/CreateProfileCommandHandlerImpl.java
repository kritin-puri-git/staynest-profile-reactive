package com.example.staynest.profile.reactive.command.create_profile;

import com.example.staynest.profile.reactive.domain.UserProfile;
import io.github.kritin_puri_git.crypto.facade.CryptoFacade;
import io.github.kritin_puri_git.crypto.hashing.model.HashingResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CreateProfileCommandHandlerImpl implements CreateProfileCommandHandler{


    private static final long INITIAL_VERSION_VALUE = 1;

    private final CryptoFacade cryptoFacade;
    private final CreateProfileCommandRepository repository;

    public CreateProfileCommandHandlerImpl(
            CryptoFacade cryptoFacade,
            CreateProfileCommandRepository repository
    ){
        this.cryptoFacade = cryptoFacade;
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Void> handle(CreateProfileCommand command) {

        String plainPublicId = cryptoFacade.decrypt(
                command.publicId(),
                command.encryptionKeyId(),
                command.encryptionVersion()
        );

        HashingResult publicIdHash = cryptoFacade.getHashResult(plainPublicId);


        UserProfile userProfile = new UserProfile(command.id(), INITIAL_VERSION_VALUE);

        userProfile.setPublicId(command.publicId());
        userProfile.setPublicIdHash(publicIdHash.hash());

        userProfile.setEncryptionVersion(command.encryptionVersion());
        userProfile.setEncryptionKeyId(command.encryptionKeyId());

        userProfile.setHashingVersion(publicIdHash.version());
        userProfile.setHashingKeyId(publicIdHash.keyId());

        return this.repository.initiate(userProfile);
    }
}