package com.emersun.rashin.repositories;

import com.emersun.rashin.collections.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveMongoRepository<Role,String> {
    Mono<Role> findByName(String name);
    Mono<Boolean> existsByName(String name);
}
