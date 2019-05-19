package com.emersun.rashin.panel.agent.services;

import com.emersun.rashin.collections.Agent;
import com.emersun.rashin.panel.agent.dtos.AgentAddDto;
import com.emersun.rashin.panel.agent.dtos.AgentDto;
import reactor.core.publisher.Mono;

public interface AgentService {
    Mono<AgentDto> create(AgentAddDto agentAddDto);
    Mono<AgentDto> update(AgentDto agentDto);
    Mono<Agent> delete(String id);
}
