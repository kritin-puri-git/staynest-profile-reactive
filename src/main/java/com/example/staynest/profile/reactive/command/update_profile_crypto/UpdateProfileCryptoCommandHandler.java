package com.example.staynest.profile.reactive.command.update_profile_crypto;

import com.example.staynest.profile.reactive.domain.UserProfile;
import reactor.core.publisher.Mono;

public interface UpdateProfileCryptoCommandHandler {

    Mono<Void> handle(UserProfile userProfile);
}