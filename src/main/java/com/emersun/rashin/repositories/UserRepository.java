package com.emersun.rashin.repositories;

import com.emersun.rashin.collections.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User,String> {
    Mono<User> findByMobile(String mobile);
}
