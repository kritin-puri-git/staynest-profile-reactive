package com.example.staynest.profile.reactive.presentation;


import com.example.staynest.profile.reactive.query.get_search_results.GetSearchResultsQueryHandler;
import com.example.staynest.profile.reactive.query.get_search_results.SearchResultsQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/profile/search")
public class SearchProfilesController {

    private final GetSearchResultsQueryHandler queryHandler;

    public SearchProfilesController(
            GetSearchResultsQueryHandler queryHandler
    ){
        this.queryHandler = queryHandler;
    }

    @GetMapping("/user")
    public Flux<SearchResultsQuery> searchUser(
            @RequestParam("q") String text
    ) {

        return this.queryHandler.get(text);
    }
}
