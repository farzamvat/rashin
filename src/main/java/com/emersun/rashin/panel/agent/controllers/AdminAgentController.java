package com.emersun.rashin.panel.agent.controllers;

import com.emersun.rashin.panel.agent.dtos.AgentAddDto;
import com.emersun.rashin.panel.agent.dtos.AgentDto;
import com.emersun.rashin.panel.agent.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${api.base.url}" + "/admin/agents")
public class AdminAgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping
    public Mono<ResponseEntity<AgentDto>> create(@RequestBody @Validated AgentAddDto agentAddDto) {
        return agentService.create(agentAddDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping
    public Mono<ResponseEntity<AgentDto>> update(@RequestBody @Validated AgentDto agentDto) {
        return agentService.update(agentDto)
                .map(ResponseEntity.accepted()::body);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return agentService.delete(id)
                .map(aVoid -> ResponseEntity.accepted().build());
    }
}
