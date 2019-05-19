package com.emersun.rashin.panel.agent.dtos;

import com.emersun.rashin.collections.Agent;
import com.emersun.rashin.dto.RoleDto;

import java.time.LocalDateTime;

public class AgentDto extends AgentAddDto {
    private String id;
    private LocalDateTime createdAt;
    private String apiKey;
    private RoleDto role;

    public AgentDto() {}

    public AgentDto(Agent agent) {
        this.setId(agent.getId());
        this.setUsername(agent.getUsername());
        this.setCanReadFromDate(agent.getCanReadFromDate());
        this.setCreatedAt(agent.getCreatedAt());
        this.setCanReadFromDate(agent.getCanReadFromDate());
        this.setApiKey(agent.getApiKey());
        this.setRole(new RoleDto(agent.getRole()));
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
