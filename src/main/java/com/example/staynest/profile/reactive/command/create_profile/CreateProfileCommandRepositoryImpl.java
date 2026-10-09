package com.example.staynest.profile.reactive.command.create_profile;

import com.example.staynest.profile.reactive.domain.UserProfile;
import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.entity.UsersProfileEntity;
import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.repository.UsersProfileR2dbcRepository;
import com.example.staynest.profile.reactive.validation.Validation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Repository
public class CreateProfileCommandRepositoryImpl implements CreateProfileCommandRepository{

    private static final String CLASS_NAME = CreateProfileCommandRepositoryImpl.class.getSimpleName();


    private final UsersProfileR2dbcRepository repository;

    public CreateProfileCommandRepositoryImpl(
            UsersProfileR2dbcRepository repository
    ){
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Void> initiate(UserProfile userProfile) {

        Long id = userProfile.getId();
        byte[] publicId = userProfile.getPublicId();
        byte[] publicIdHash = userProfile.getPublicIdHash();

        Short encryptionVersion = userProfile.getEncryptionVersion();
        Short encryptionKeyId = userProfile.getEncryptionKeyId();
        Short hashingVersion = userProfile.getHashingVersion();
        Short hashingKeyId = userProfile.getHashingKeyId();

        long version = userProfile.getVersion();

        Validation.validate(id, "id", CLASS_NAME);
        Validation.validate(publicId, "publicId", CLASS_NAME);
        Validation.validate(publicIdHash, "publicIdHash", CLASS_NAME);
        Validation.validate(encryptionVersion, "encryptionVersion", CLASS_NAME);
        Validation.validate(encryptionKeyId, "encryptionKeyId", CLASS_NAME);
        Validation.validate(hashingVersion, "hashingVersion", CLASS_NAME);
        Validation.validate(hashingKeyId, "hashingKeyId", CLASS_NAME);

        UsersProfileEntity entity = new UsersProfileEntity();

        entity.setAsNew();

        entity.setId(id);
        entity.setPublicId(publicId);
        entity.setPublicIdHash(publicIdHash);

        entity.setEncryptionVersion(encryptionVersion);
        entity.setEncryptionKeyId(encryptionKeyId);
        entity.setHashingVersion(hashingVersion);
        entity.setHashingKeyId(hashingKeyId);

        entity.setVersion(version);

        return repository.existsById(id)
                .flatMap(exists -> {
                    if(!exists){
                        return repository.save(entity)
                                .then();
                    }

                    return Mono.empty();
                })
                .toFuture();

    }
}
