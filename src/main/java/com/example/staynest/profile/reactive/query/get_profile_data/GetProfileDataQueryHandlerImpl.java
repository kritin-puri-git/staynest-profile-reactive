package com.example.staynest.profile.reactive.query.get_profile_data;

import com.example.staynest.profile.reactive.command.update_profile_crypto.UpdateProfileCryptoCommandHandler;
import com.example.staynest.profile.reactive.domain.UserProfile;
import com.example.staynest.profile.reactive.validation.Validation;
import io.github.kritin_puri_git.crypto.facade.CryptoFacade;
import reactor.core.publisher.Mono;

import java.util.List;

public class GetProfileDataQueryHandlerImpl implements GetProfileDataQueryHandler {

    private static final String CLASS_NAME = GetProfileDataQueryHandlerImpl.class.getSimpleName();

    private final CryptoFacade cryptoFacade;
    private final GetProfileDataQueryRepository repository;
    private final UpdateProfileCryptoCommandHandler commandHandler;
    public GetProfileDataQueryHandlerImpl(
            CryptoFacade cryptoFacade,
            GetProfileDataQueryRepository repository,
            UpdateProfileCryptoCommandHandler commandHandler
    ){
        this.cryptoFacade = cryptoFacade;
        this.repository = repository;
        this.commandHandler = commandHandler;
    }

    @Override
    public Mono<UserProfile> handle(String publicId) {

        Validation.validate(publicId, "publicId", CLASS_NAME);

        List<byte[]> publicIdHashList = this.cryptoFacade.getHashCandidates(publicId);

        return this.repository.get(publicIdHashList)
                .filter(userProfile ->
                        publicId.equalsIgnoreCase(
                                this.cryptoFacade.decrypt(
                                        userProfile.getPublicId(),
                                        userProfile.getEncryptionKeyId(),
                                        userProfile.getEncryptionVersion()
                                )
                        )
                )
                .next()
                .switchIfEmpty(Mono.error(new IllegalStateException("Profile Not Found")))
                .delayUntil(this.commandHandler::handle);
    }
}
