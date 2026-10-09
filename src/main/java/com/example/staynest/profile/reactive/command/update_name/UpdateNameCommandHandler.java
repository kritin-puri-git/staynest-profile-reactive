package com.example.staynest.profile.reactive.command.update_name;

import reactor.core.publisher.Mono;

public interface UpdateNameCommandHandler {

    Mono<Void> handle(UpdateNameCommand command);
}
