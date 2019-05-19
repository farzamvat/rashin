package com.emersun.rashin.collections;

import com.emersun.rashin.dto.RoleDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class Role {
    @Id
    private String id;
    private String name;
    private Set<Permission> permissions;

    public Role() {
    }

    public Role(RoleDto roleDto) {
        this.name = roleDto.getName();
        this.permissions = roleDto.getPermissions();
    }

    public Role update(RoleDto roleDto) {
        this.name = roleDto.getName();
        this.permissions = roleDto.getPermissions();
        this.id = roleDto.getId();
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
