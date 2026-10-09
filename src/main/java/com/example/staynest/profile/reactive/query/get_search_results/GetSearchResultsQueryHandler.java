package com.example.staynest.profile.reactive.query.get_search_results;

import reactor.core.publisher.Flux;

public interface GetSearchResultsQueryHandler {

    Flux<SearchResultsQuery> get(String text);
}
