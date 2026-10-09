package com.example.staynest.profile.reactive.command.create_profile;

import com.example.staynest.profile.reactive.domain.UserProfile;

import java.util.concurrent.CompletableFuture;

public interface CreateProfileCommandRepository {

    CompletableFuture<Void> initiate(UserProfile userProfile);
}
