package com.example.staynest.profile.reactive.security.authentication;

import com.example.staynest.profile.reactive.validation.Validation;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PublicIdAuthentication extends AbstractAuthenticationToken {


    private final String publicId;
    public PublicIdAuthentication(String publicId){
        super((Collection<? extends GrantedAuthority>) null);

        Validation.validate(publicId, "publicId", this.getClass().getSimpleName());
        this.publicId = publicId;
        setAuthenticated(true);
    }


    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return this.publicId;
    }

}
