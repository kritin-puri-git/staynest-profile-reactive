package com.example.staynest.profile.reactive.query.get_encryption_data;

import com.example.staynest.profile.reactive.domain.UserProfile;
import reactor.core.publisher.Flux;

import java.util.List;

public interface GetEncryptionDataQueryRepository {

    Flux<UserProfile> get(List<byte[]> publicIdHashList);
}
