package com.example.staynest.profile.reactive.query.get_search_results;

import com.example.staynest.profile.reactive.domain.SearchProfiles;
import reactor.core.publisher.Flux;

public interface GetSearchResultsQueryRepository {

    Flux<SearchProfiles> get(String text);
}