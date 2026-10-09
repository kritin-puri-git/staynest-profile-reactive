package com.example.staynest.profile.reactive.command.create_profile;

import com.example.staynest.profile.reactive.validation.Validation;

public record CreateProfileCommand(
        Long id,
        byte[] publicId,
        Short encryptionVersion,
        Short encryptionKeyId
) {

    public CreateProfileCommand{
        String className = this.getClass().getSimpleName();
        Validation.validate(id, "id", className);
        Validation.validate(publicId, "publicId", className);
        Validation.validate(encryptionVersion, "encryptionVersion", className);
        Validation.validate(encryptionKeyId, "encryptionKeyId", className);
    }
}
