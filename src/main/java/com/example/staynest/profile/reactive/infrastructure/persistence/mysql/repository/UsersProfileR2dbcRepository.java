package com.example.staynest.profile.reactive.infrastructure.persistence.mysql.repository;

import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.entity.UsersProfileEntity;
import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.projection.EncryptionDataProjection;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UsersProfileR2dbcRepository extends
        ReactiveCrudRepository<UsersProfileEntity, Long> {

    Flux<EncryptionDataProjection> findEncryptionDataByPublicIdHashIn(List<byte[]> publicIdHashList);
    Flux<UsersProfileEntity> findUsersProfileByPublicIdHashIn(List<byte[]> publicIdHashList);
    Mono<UsersProfileEntity> findByPublicIdHashAndVersion(byte[] publicIdHash, long version);


}