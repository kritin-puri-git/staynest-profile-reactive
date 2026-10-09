package com.example.staynest.profile.reactive.infrastructure.persistence.mysql.projection;

public interface EncryptionDataProjection {

    byte[] getPublicId();
    byte[] getPublicIdHash();
    Short getEncryptionVersion();
    Short getEncryptionKeyId();
    Long getVersion();
}
