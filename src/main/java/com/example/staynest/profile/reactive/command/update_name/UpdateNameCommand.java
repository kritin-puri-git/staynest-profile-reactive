package com.example.staynest.profile.reactive.command.update_name;

import com.example.staynest.profile.reactive.validation.Validation;

public record UpdateNameCommand(
        String publicId,
        String name
) {

    public UpdateNameCommand{
        String className = this.getClass().getSimpleName();
        Validation.validate(publicId, "publicId", className);
        Validation.validate(name, "name", className);
    }
}
