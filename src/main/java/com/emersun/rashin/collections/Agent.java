package com.emersun.rashin.collections;

import com.emersun.rashin.panel.agent.dtos.AgentAddDto;
import io.vavr.control.Option;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document
public class Agent {
    @Id
    private String id;
    private String username;
    private String password;
    private Boolean isBanned = false;
    private LocalDateTime createdAt;
    private LocalDateTime canReadFromDate;
    private String apiKey;
    private Boolean isDeleted = false;
    private Set<AgentApplication> applications = new HashSet<>();
    @DBRef
    private Role role;

    public Agent() {
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Set<AgentApplication> getApplications() {
        return applications;
    }

    public void setApplications(Set<AgentApplication> applications) {
        this.applications = applications;
    }

    public Agent(Role role, AgentAddDto agentAddDto) {
        this.role = role;
        this.username = agentAddDto.getUsername();
        this.password = agentAddDto.getPassword();
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Tehran"));
        Option.of(agentAddDto.getCanReadFromDate())
                .peek(this::setCanReadFromDate)
                .onEmpty(() -> this.setCanReadFromDate(this.createdAt));
        this.apiKey = UUID.randomUUID().toString();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCanReadFromDate() {
        return canReadFromDate;
    }

    public void setCanReadFromDate(LocalDateTime canReadFromDate) {
        this.canReadFromDate = canReadFromDate;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
