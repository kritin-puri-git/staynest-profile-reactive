package com.example.staynest.profile.reactive.query.get_profile_data;

import com.example.staynest.profile.reactive.domain.UserProfile;
import reactor.core.publisher.Flux;

import java.util.List;

public interface GetProfileDataQueryRepository {

    Flux<UserProfile> get(List<byte[]> publicIdHashList);
}
