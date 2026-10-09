package com.example.staynest.profile.reactive.command.create_elasticsearch_document;

import com.example.staynest.profile.reactive.command.create_profile.CreateProfileCommand;

import java.util.concurrent.CompletableFuture;

public interface CreateSearchDocumentCommandHandler {
    CompletableFuture<Void> handle(CreateSearchDocumentCommand command);
}
