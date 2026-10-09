package com.example.staynest.profile.reactive.command.create_elasticsearch_document;

import com.example.staynest.profile.reactive.validation.Validation;



public record CreateSearchDocumentCommand(
        Long id,
        byte[] publicId,
        byte[] username,
        Short encryptionVersion,
        Short encryptionKeyId
) {

    public CreateSearchDocumentCommand{
        String className = this.getClass().getSimpleName();
        Validation.validate(id, "id", className);
        Validation.validate(publicId, "publicId", className);
        Validation.validate(username, "username", className);
        Validation.validate(encryptionVersion, "encryptionVersion", className);
        Validation.validate(encryptionKeyId, "encryptionKeyId", className);
    }
}