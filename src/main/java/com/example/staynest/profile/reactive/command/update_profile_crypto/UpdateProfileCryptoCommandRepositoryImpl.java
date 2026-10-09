package com.example.staynest.profile.reactive.command.update_profile_crypto;

import com.example.staynest.profile.reactive.domain.UserProfile;
import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.entity.UsersProfileEntity;
import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.repository.UsersProfileR2dbcRepository;
import com.example.staynest.profile.reactive.validation.Validation;
import org.springframework.dao.OptimisticLockingFailureException;
import reactor.core.publisher.Mono;

public class UpdateProfileCryptoCommandRepositoryImpl
        implements UpdateProfileCryptoCommandRepository{

    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final UsersProfileR2dbcRepository repository;

    public UpdateProfileCryptoCommandRepositoryImpl(
            UsersProfileR2dbcRepository repository
    ){
        this.repository = repository;
    }

    @Override
    public Mono<Void> update(UserProfile profile) {
        return Mono.defer(()->{
            Validation.validate(profile, "profile", CLASS_NAME);

            Long id = profile.getId();
            byte[] publicId = profile.getPublicId();
            byte[] publicIdHash = profile.getPublicIdHash();

            Short encryptionVersion = profile.getEncryptionVersion();
            Short encryptionKeyId = profile.getEncryptionKeyId();
            Short hashingVersion = profile.getHashingVersion();
            Short hashingKeyId = profile.getHashingKeyId();

            Long version = profile.getVersion();

            try{
                Validation.validate(id, "id", CLASS_NAME);
                Validation.validate(publicId, "publicId", CLASS_NAME);
                Validation.validate(publicIdHash, "publicIdHash", CLASS_NAME);
                Validation.validate(encryptionKeyId, "encryptionKeyId", CLASS_NAME);
                Validation.validate(encryptionVersion, "encryptionVersion", CLASS_NAME);
                Validation.validate(hashingKeyId, "hashingKeyId", CLASS_NAME);
                Validation.validate(hashingVersion, "hashingVersion", CLASS_NAME);
                Validation.validate(version, "version", CLASS_NAME);
            } catch (IllegalArgumentException e) {
                return Mono.empty();
            }


            UsersProfileEntity entity = new UsersProfileEntity();

            entity.setId(id);
            entity.setPublicId(publicId);
            entity.setPublicIdHash(publicIdHash);

            entity.setName(profile.getName());
            entity.setBio(profile.getBio());
            entity.setProfilePictureUrl(profile.getProfilePictureUrl());
            entity.setDateOfBirth(profile.getDateOfBirth());
            entity.setGender(profile.getGender());

            entity.setEncryptionKeyId(encryptionKeyId);
            entity.setEncryptionVersion(encryptionVersion);
            entity.setHashingKeyId(hashingKeyId);
            entity.setHashingVersion(hashingVersion);

            entity.setVersion(version);

            return this.repository.save(entity);
        })
                .onErrorResume(
                        OptimisticLockingFailureException.class,
                        ex -> Mono.empty()
                )
                .then();
    }
}
