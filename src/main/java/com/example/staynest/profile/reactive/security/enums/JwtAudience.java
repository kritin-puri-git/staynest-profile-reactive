package com.example.staynest.profile.reactive.security.enums;

public enum JwtAudience {
    STAYNEST_PROFILE
    ;


    public static JwtAudience from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("audience role cannot be null");
        }

        try {
            return JwtAudience.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Unknown Jwt audience: " + value,
                    exception
            );
        }
    }
}
