package com.example.staynest.profile.reactive.command.update_profile_crypto;

import com.example.staynest.profile.reactive.domain.UserProfile;
import com.example.staynest.profile.reactive.validation.Validation;
import io.github.kritin_puri_git.crypto.encryption.model.EncryptionResultMap;
import io.github.kritin_puri_git.crypto.facade.CryptoFacade;
import io.github.kritin_puri_git.crypto.hashing.model.HashingResult;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileCryptoCommandHandlerImpl
        implements UpdateProfileCryptoCommandHandler {

    private static final String PUBLIC_ID_MAP_KEY = "publicId";
    private static final String NAME_MAP_KEY = "name";
    private static final String BIO_MAP_KEY = "bio";
    private static final String PROFILE_PICTURE_URL_MAP_KEY = "profilePictureUrl";
    private static final String DATE_OF_BIRTH_MAP_KEY = "dateOfBirth";
    private static final String GENDER_MAP_KEY = "gender";


    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final CryptoFacade cryptoFacade;
    private final UpdateProfileCryptoCommandRepository repository;

    public UpdateProfileCryptoCommandHandlerImpl(
            CryptoFacade cryptoFacade,
            UpdateProfileCryptoCommandRepository repository
    ){
        this.cryptoFacade = cryptoFacade;
        this.repository = repository;
    }


    @Override
    public Mono<Void> handle(UserProfile userProfile) {

        return Mono.defer(()->{
            Long id = userProfile.getId();
            byte[] publicId = userProfile.getPublicId();
            byte[] publicIdHash = userProfile.getPublicIdHash();

            byte[] name = userProfile.getName();
            byte[] bio = userProfile.getBio();
            byte[] profilePictureUrl = userProfile.getProfilePictureUrl();
            byte[] dateOfBirth = userProfile.getDateOfBirth();
            byte[] gender = userProfile.getGender();

            Short encryptionVersion = userProfile.getEncryptionVersion();
            Short encryptionKeyId = userProfile.getEncryptionKeyId();
            Short hashingVersion = userProfile.getHashingVersion();
            Short hashingKeyId = userProfile.getHashingKeyId();

            Long version = userProfile.getVersion();

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

            boolean encryptionLatest = this.cryptoFacade.isLatestEncryption(
                    encryptionKeyId,
                    encryptionVersion
            );
            boolean hashingLatest = this.cryptoFacade.isLatestHashing(hashingKeyId, hashingVersion);

            if(!hashingLatest){
                String plainPublicId = this.cryptoFacade.decrypt(
                        publicId,
                        encryptionKeyId,
                        encryptionVersion
                );
                HashingResult result = this.cryptoFacade.getHashResult(plainPublicId);
                publicIdHash = result.hash();
                Validation.validate(publicIdHash, "publicIdHash", CLASS_NAME);

                hashingKeyId = result.keyId();
                hashingVersion = result.version();

                userProfile.setPublicIdHash(publicIdHash);
                userProfile.setHashingKeyId(hashingKeyId);
                userProfile.setHashingVersion(hashingVersion);
            }

            if(!encryptionLatest){
                Map<String, byte[]> encryptedDataMap = new HashMap<>();

                encryptedDataMap.put(PUBLIC_ID_MAP_KEY, publicId);

                if(name != null && name.length != 0)
                    encryptedDataMap.put(NAME_MAP_KEY, name);

                if(bio != null && bio.length != 0)
                    encryptedDataMap.put(BIO_MAP_KEY, bio);

                if(profilePictureUrl != null && profilePictureUrl.length != 0)
                    encryptedDataMap.put(PROFILE_PICTURE_URL_MAP_KEY, profilePictureUrl);

                if(dateOfBirth != null && dateOfBirth.length != 0)
                    encryptedDataMap.put(DATE_OF_BIRTH_MAP_KEY, dateOfBirth);

                if(gender != null && gender.length != 0)
                    encryptedDataMap.put(GENDER_MAP_KEY, gender);

                EncryptionResultMap encryptionResultMap =
                        this.cryptoFacade.rotateDataMap(
                                encryptedDataMap,
                                encryptionKeyId,
                                encryptionVersion
                        );
                encryptedDataMap = encryptionResultMap.encryptedDataMap();

                publicId = encryptedDataMap.get(PUBLIC_ID_MAP_KEY);
                Validation.validate(publicId, "publicId", CLASS_NAME);

                userProfile.setPublicId(publicId);

                userProfile.setName(encryptedDataMap.get(NAME_MAP_KEY));
                userProfile.setBio(encryptedDataMap.get(BIO_MAP_KEY));
                userProfile.setProfilePictureUrl(encryptedDataMap.get(PROFILE_PICTURE_URL_MAP_KEY));
                userProfile.setDateOfBirth(encryptedDataMap.get(DATE_OF_BIRTH_MAP_KEY));
                userProfile.setGender(encryptedDataMap.get(GENDER_MAP_KEY));

                userProfile.setEncryptionKeyId(encryptionResultMap.keyId());
                userProfile.setEncryptionVersion(encryptionResultMap.version());
            }


            if(!encryptionLatest || !hashingLatest)
                return this.repository.update(userProfile);
            return Mono.empty();
        });
    }
}