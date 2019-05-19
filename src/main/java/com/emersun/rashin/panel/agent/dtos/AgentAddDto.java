package com.emersun.rashin.panel.agent.dtos;

import com.emersun.rashin.dto.AbstractBaseEntityDto;
import com.emersun.rashin.utils.DateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class AgentAddDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime canReadFromDate;
    @NotEmpty
    private String roleId;

    public AgentAddDto() {

    }

    public AgentAddDto(String username,String password, String roleId) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRoleId(roleId);
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public LocalDateTime getCanReadFromDate() {
        return canReadFromDate;
    }

    public void setCanReadFromDate(LocalDateTime canReadFromDate) {
        this.canReadFromDate = canReadFromDate;
    }
}
