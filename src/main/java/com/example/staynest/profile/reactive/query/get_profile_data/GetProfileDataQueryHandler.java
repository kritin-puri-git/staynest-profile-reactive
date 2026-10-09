package com.example.staynest.profile.reactive.query.get_profile_data;

import com.example.staynest.profile.reactive.domain.UserProfile;
import reactor.core.publisher.Mono;

public interface GetProfileDataQueryHandler {

    Mono<UserProfile> handle(String publicIDHash);
}
