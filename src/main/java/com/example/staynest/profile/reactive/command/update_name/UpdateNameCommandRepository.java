package com.example.staynest.profile.reactive.command.update_name;

import com.example.staynest.profile.reactive.domain.UserProfile;
import reactor.core.publisher.Mono;

public interface UpdateNameCommandRepository {

    Mono<Void> update(UserProfile userProfile);
}
