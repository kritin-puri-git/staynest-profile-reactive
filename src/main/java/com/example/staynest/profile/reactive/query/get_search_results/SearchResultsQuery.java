package com.example.staynest.profile.reactive.query.get_search_results;

import com.example.staynest.profile.reactive.validation.Validation;

public record SearchResultsQuery(
        String publicId,
        String name,
        String username,
        String profilePictureUrl
) {

    public SearchResultsQuery {

        String className = this.getClass().getSimpleName();

        Validation.validate(publicId, "publicId", className);
        Validation.validate(username, "username", className);
    }
}
