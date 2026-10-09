package com.example.staynest.profile.reactive.query.get_encryption_data;

import com.example.staynest.profile.reactive.domain.UserProfile;
import com.example.staynest.profile.reactive.validation.Validation;
import io.github.kritin_puri_git.crypto.facade.CryptoFacade;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetEncryptionDataQueryHandlerImpl implements GetEncryptionDataQueryHandler {

    private static final String CLASS_NAME =
            GetEncryptionDataQueryHandlerImpl.class.getSimpleName();

    private final CryptoFacade cryptoFacade;
    private final GetEncryptionDataQueryRepository repository;

    public GetEncryptionDataQueryHandlerImpl(
            CryptoFacade cryptoFacade,
            GetEncryptionDataQueryRepository repository
    ){
        this.cryptoFacade = cryptoFacade;
        this.repository = repository;
    }


    @Override
    public Mono<UserProfile> handle(String publicId) {

        return Mono.defer(()->{
            Validation.validate(publicId, "publicID", CLASS_NAME);

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
                    ;
        });

    }
}
