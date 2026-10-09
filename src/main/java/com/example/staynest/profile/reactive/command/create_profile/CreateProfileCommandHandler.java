package com.example.staynest.profile.reactive.command.create_profile;

import java.util.concurrent.CompletableFuture;

public interface CreateProfileCommandHandler {

    CompletableFuture<Void> handle(CreateProfileCommand command);
}
