package com.emersun.rashin.panel.agent.repositories;

import com.emersun.rashin.collections.Agent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AgentRepository extends ReactiveMongoRepository<Agent,String> {
    Mono<Agent> findByUsername(String username);
}
