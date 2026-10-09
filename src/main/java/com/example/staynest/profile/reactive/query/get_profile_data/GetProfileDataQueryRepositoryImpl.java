package com.example.staynest.profile.reactive.query.get_profile_data;

import com.example.staynest.profile.reactive.domain.UserProfile;
import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.repository.UsersProfileR2dbcRepository;
import com.example.staynest.profile.reactive.validation.Validation;
import reactor.core.publisher.Flux;

import java.util.List;

public class GetProfileDataQueryRepositoryImpl implements GetProfileDataQueryRepository {

    private static final String CLASS_NAME =
            GetProfileDataQueryRepositoryImpl.class.getSimpleName();

    private final UsersProfileR2dbcRepository repository;

    public GetProfileDataQueryRepositoryImpl(
            UsersProfileR2dbcRepository repository
    ){
        this.repository = repository;
    }

    @Override
    public Flux<UserProfile> get(List<byte[]> publicIdHashList) {


        return this.repository.findUsersProfileByPublicIdHashIn(publicIdHashList)
                .filter(entity -> {
                    Long id = entity.getId();
                    byte[] publicId = entity.getPublicId();
                    byte[] publicIdHash = entity.getPublicIdHash();

                    Short encryptionKeyId = entity.getEncryptionKeyId();
                    Short encryptionVersion = entity.getEncryptionVersion();
                    Short hashingKeyId = entity.getHashingKeyId();
                    Short hashingVersion = entity.getHashingVersion();

                    Long version = entity.getVersion();

                    try {

                        Validation.validate(id, "id", CLASS_NAME);
                        Validation.validate(publicId, "publicId", CLASS_NAME);
                        Validation.validate(publicIdHash, "publicIdHash", CLASS_NAME);
                        Validation.validate(encryptionKeyId, "encryptionKeyId", CLASS_NAME);
                        Validation.validate(encryptionVersion, "encryptionVersion", CLASS_NAME);
                        Validation.validate(hashingKeyId, "hashingKeyId", CLASS_NAME);
                        Validation.validate(hashingVersion, "hashingVersion", CLASS_NAME);
                        Validation.validate(version, "version", CLASS_NAME);

                        return true;

                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                })
                .map(entity -> {


                    UserProfile userProfile = new UserProfile(
                            entity.getId(),
                            entity.getVersion()
                    );

                    userProfile.setPublicId(entity.getPublicId());
                    userProfile.setPublicIdHash((entity.getPublicIdHash()));
                    userProfile.setEncryptionKeyId(entity.getEncryptionKeyId());
                    userProfile.setEncryptionVersion(entity.getEncryptionVersion());
                    userProfile.setHashingKeyId(entity.getHashingKeyId());
                    userProfile.setHashingVersion(entity.getHashingVersion());

                    userProfile.setName(entity.getName());
                    userProfile.setBio(entity.getBio());
                    userProfile.setProfilePictureUrl(entity.getProfilePictureUrl());
                    userProfile.setDateOfBirth(entity.getDateOfBirth());
                    userProfile.setGender(entity.getGender());

                    return userProfile;

                });
    }
}
