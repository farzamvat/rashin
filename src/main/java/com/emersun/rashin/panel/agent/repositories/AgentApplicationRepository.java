package com.emersun.rashin.panel.agent.repositories;

import com.emersun.rashin.collections.Agent;
import com.emersun.rashin.collections.AgentApplication;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface AgentApplicationRepository extends ReactiveMongoRepository<AgentApplication,String> {
    Optional<Agent> findByAgentId(String id);
    Optional<Agent> findByAgentUsername(String username);
}
