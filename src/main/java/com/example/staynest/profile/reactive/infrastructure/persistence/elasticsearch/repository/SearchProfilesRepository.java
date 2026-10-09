package com.example.staynest.profile.reactive.infrastructure.persistence.elasticsearch.repository;

import com.example.staynest.profile.reactive.infrastructure.persistence.elasticsearch.document.ProfilesDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface SearchProfilesRepository
        extends ReactiveElasticsearchRepository<ProfilesDocument, Long> {
}
