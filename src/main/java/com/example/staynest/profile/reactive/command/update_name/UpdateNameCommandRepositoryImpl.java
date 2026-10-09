package com.example.staynest.profile.reactive.command.update_name;

import com.example.staynest.profile.reactive.domain.UserProfile;
import com.example.staynest.profile.reactive.infrastructure.persistence.mysql.repository.UsersProfileR2dbcRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UpdateNameCommandRepositoryImpl implements UpdateNameCommandRepository {

    private final UsersProfileR2dbcRepository repository;
    public UpdateNameCommandRepositoryImpl(
            UsersProfileR2dbcRepository repository
    ){
        this.repository = repository;
    }

    @Override
    public Mono<Void> update(UserProfile userProfile) {

        return this.repository.findByPublicIdHashAndVersion(userProfile.getPublicIdHash(), userProfile.getVersion())
                .switchIfEmpty(Mono.error(new IllegalStateException("Name cannot be updated. please try again")))
                .flatMap(entity->{
                    entity.setName(userProfile.getName());
                    return this.repository.save(entity);
                })
                .onErrorMap(
                        OptimisticLockingFailureException.class,
                        ex -> new IllegalStateException(
                                "Profile was modified by another request. Please try again",
                                ex
                        )
                )
                .then();
    }
}
