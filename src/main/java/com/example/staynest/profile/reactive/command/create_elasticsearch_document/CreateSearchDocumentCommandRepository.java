package com.example.staynest.profile.reactive.command.create_elasticsearch_document;

import com.example.staynest.profile.reactive.domain.SearchProfiles;
import com.example.staynest.profile.reactive.domain.UserProfile;

import java.util.concurrent.CompletableFuture;

public interface CreateSearchDocumentCommandRepository {
    CompletableFuture<Void> initiate(SearchProfiles profiles);
}
