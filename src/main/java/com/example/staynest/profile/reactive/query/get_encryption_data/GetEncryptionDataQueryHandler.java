package com.example.staynest.profile.reactive.query.get_encryption_data;

import com.example.staynest.profile.reactive.domain.UserProfile;
import reactor.core.publisher.Mono;

public interface GetEncryptionDataQueryHandler {

    Mono<UserProfile> handle(String publicId);
}
