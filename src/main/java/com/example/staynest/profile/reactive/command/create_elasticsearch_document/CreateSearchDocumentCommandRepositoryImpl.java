package com.example.staynest.profile.reactive.command.create_elasticsearch_document;

import com.example.staynest.profile.reactive.domain.SearchProfiles;
import com.example.staynest.profile.reactive.infrastructure.persistence.elasticsearch.document.ProfilesDocument;
import com.example.staynest.profile.reactive.infrastructure.persistence.elasticsearch.repository.SearchProfilesRepository;
import com.example.staynest.profile.reactive.validation.Validation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;


@Repository
public class CreateSearchDocumentCommandRepositoryImpl implements CreateSearchDocumentCommandRepository {


    private static final String CLASS_NAME = CreateSearchDocumentCommandRepositoryImpl.class.getSimpleName();

    private final SearchProfilesRepository repository;

    public CreateSearchDocumentCommandRepositoryImpl(
            SearchProfilesRepository repository
    ){
        this.repository = repository;
    }


    @Override
    public CompletableFuture<Void> initiate(SearchProfiles profiles) {

        Long id = profiles.getId();
        String publicId = profiles.getPublicId();
        String username = profiles.getUsername();

        Validation.validate(id, "id", CLASS_NAME);
        Validation.validate(publicId, "publicId", CLASS_NAME);
        Validation.validate(username, "username", CLASS_NAME);

        ProfilesDocument document = new ProfilesDocument();

        document.setId(id);
        document.setPublicId(publicId);
        document.setUsername(username);


        return this.repository.existsById(id)
                .flatMap(exists->{
                    if(!exists){
                        return this.repository.save(document)
                                .then();
                    }

                    return Mono.empty();
                })
                .toFuture();
    }
}
