package com.example.staynest.profile.reactive.query.get_search_results;

import com.example.staynest.profile.reactive.validation.Validation;
import io.github.kritin_puri_git.crypto.facade.CryptoFacade;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetSearchResultsQueryHandlerImpl implements GetSearchResultsQueryHandler {


    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final GetSearchResultsQueryRepository repository;
    private final CryptoFacade cryptoFacade;

    public GetSearchResultsQueryHandlerImpl(
            GetSearchResultsQueryRepository repository,
            CryptoFacade cryptoFacade
    ){
        this.cryptoFacade = cryptoFacade;
        this.repository = repository;
    }

    @Override
    public Flux<SearchResultsQuery> get(String text) {

        return Flux.defer(()->{
            return this.repository.get(text)
                    .map(profiles -> {

                        String publicId = profiles.getPublicId();
                        String username = profiles.getUsername();

                        Validation.validate(publicId, "publicId", CLASS_NAME);
                        Validation.validate(username, "username", CLASS_NAME);

                        byte[] profilePictureUrl = profiles.getProfilePictureUrl();

                        String profilePicUrl = null;


                        if(profilePictureUrl != null){
                            short encryptionKeyId = profiles.getEncryptionKeyId();
                            short encryptionVersion = profiles.getEncryptionVersion();

                            profilePicUrl = this.cryptoFacade.decrypt(
                                    profilePictureUrl,
                                    encryptionKeyId,
                                    encryptionVersion
                            );
                        }

                        return new SearchResultsQuery(
                                publicId,
                                profiles.getName(),
                                username,
                                profilePicUrl
                        );

                    });
        });
    }
}
