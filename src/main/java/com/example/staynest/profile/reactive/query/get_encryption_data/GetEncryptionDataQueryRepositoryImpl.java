package com.example.staynest.profile.reactive.query.get_encryption_data;

import com.example.staynest.profile.reactive.domain.UserProfile;
import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.repository.UsersProfileR2dbcRepository;
import com.example.staynest.profile.reactive.validation.Validation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public class GetEncryptionDataQueryRepositoryImpl implements GetEncryptionDataQueryRepository {

    private static final String CLASS_NAME =
            GetEncryptionDataQueryRepositoryImpl.class.getSimpleName();

    private final UsersProfileR2dbcRepository repository;

    public GetEncryptionDataQueryRepositoryImpl(
            UsersProfileR2dbcRepository repository
    ){
        this.repository = repository;
    }

    @Override
    public Flux<UserProfile> get(List<byte[]> publicIdHashList) {


        return this.repository.findEncryptionDataByPublicIdHashIn(publicIdHashList)
                .filter(projection->{
                    byte[] publicId = projection.getPublicId();
                    byte[] publicIdHash = projection.getPublicIdHash();
                    Short encryptionVersion = projection.getEncryptionVersion();
                    Short encryptionKeyId = projection.getEncryptionKeyId();
                    Long version = projection.getVersion();

                    try {

                        Validation.validate(publicId, "publicId", CLASS_NAME);
                        Validation.validate(publicIdHash, "publicIdHash", CLASS_NAME);
                        Validation.validate(encryptionVersion, "encryptionVersion", CLASS_NAME);
                        Validation.validate(encryptionKeyId, "encryptionKeyId", CLASS_NAME);
                        Validation.validate(version, "version", CLASS_NAME);
                    } catch (IllegalArgumentException e) {
                        return false;
                    }

                    return true;
                })
                .map(projection -> {
                    UserProfile userProfile = new UserProfile(projection.getVersion());

                    userProfile.setPublicId(projection.getPublicId());
                    userProfile.setPublicIdHash(projection.getPublicIdHash());
                    userProfile.setEncryptionVersion(projection.getEncryptionVersion());
                    userProfile.setEncryptionKeyId(projection.getEncryptionKeyId());

                    return userProfile;

                });
    }
}
