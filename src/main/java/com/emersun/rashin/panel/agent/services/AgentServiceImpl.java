package com.emersun.rashin.panel.agent.services;

import com.emersun.rashin.collections.Agent;
import com.emersun.rashin.exceptions.BadRequestException;
import com.emersun.rashin.panel.agent.dtos.AgentAddDto;
import com.emersun.rashin.panel.agent.dtos.AgentDto;
import com.emersun.rashin.panel.agent.repositories.AgentRepository;
import com.emersun.rashin.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<AgentDto> create(AgentAddDto agentAddDto) {
        return roleRepository.findById(agentAddDto.getRoleId())
                .switchIfEmpty(Mono.error(new BadRequestException("role not found")))
                .map(role -> new Agent(role,agentAddDto))
                .doOnSuccess(agent -> agent.setPassword(passwordEncoder.encode(agent.getPassword())))
                .flatMap(agentRepository::save)
                .map(AgentDto::new);
    }

    @Override
    public Mono<AgentDto> update(AgentDto agentDto) {
        return roleRepository.findById(agentDto.getRoleId())
                .switchIfEmpty(Mono.error(new BadRequestException("role not found")))
                .zipWith(agentRepository.findById(agentDto.getId())
                        .switchIfEmpty(Mono.error(new BadRequestException("agent not found"))), (role, agent) -> {
                    agent.setRole(role);
                    agent.setCanReadFromDate(agentDto.getCanReadFromDate());
                    return agent;
                })
                .flatMap(agentRepository::save)
                .map(AgentDto::new);
    }

    @Override
    public Mono<Agent> delete(String id) {
        return agentRepository.findById(id)
                .switchIfEmpty(Mono.error(new BadRequestException("agent not found")))
                .doOnSuccess(agent -> agent.setDeleted(true))
                .flatMap(agentRepository::save);
    }
}
