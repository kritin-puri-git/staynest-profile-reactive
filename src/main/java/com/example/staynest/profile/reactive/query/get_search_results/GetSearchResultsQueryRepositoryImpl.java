package com.example.staynest.profile.reactive.query.get_search_results;

import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import com.example.staynest.profile.reactive.domain.SearchProfiles;
import com.example.staynest.profile.reactive.infrastructure.persistence.elasticsearch.document.ProfilesDocument;
import com.example.staynest.profile.reactive.validation.Validation;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public class GetSearchResultsQueryRepositoryImpl implements GetSearchResultsQueryRepository {


    private final String CLASS_NAME = this.getClass().getSimpleName();
    private final ReactiveElasticsearchOperations operations;

    public GetSearchResultsQueryRepositoryImpl(
            ReactiveElasticsearchOperations operations
    ){
        this.operations = operations;
    }

    @Override
    public Flux<SearchProfiles> get(String text) {

        return Flux.defer(()->{
            Query query = NativeQuery.builder()
                    .withQuery(q->
                            q.multiMatch(m->
                                    m
                                            .query(text)
                                            .type(TextQueryType.BoolPrefix)
                                            .fields(
                                                    "username.autocomplete",
                                                    "name.autocomplete"
                                            )
                            )
                    )
                    .withMaxResults(20)
                    .build();

            return operations.search(query, ProfilesDocument.class)
                    .map(hit->{
                        ProfilesDocument document = hit.getContent();

                        String publicId = document.getPublicId();
                        String username = document.getUsername();

                        Validation.validate(publicId, "publicId", CLASS_NAME);

                        Validation.validate(username, "username", CLASS_NAME);

                        SearchProfiles profiles = new SearchProfiles();

                        profiles.setPublicId(publicId);
                        profiles.setUsername(username);

                        String name = document.getName();
                        if(name != null)
                            profiles.setName(name);


                        byte[] profilePictureUrl = document.getProfilePictureUrl();
                        if(profilePictureUrl != null)
                            profiles.setProfilePictureUrl(
                                    document.getProfilePictureUrl(),
                                    document.getEncryptionKeyId(),
                                    document.getEncryptionVersion()
                            );


                        return profiles;

                    });
        });


    }
}