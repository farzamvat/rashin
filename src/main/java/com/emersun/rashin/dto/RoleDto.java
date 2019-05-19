package com.emersun.rashin.dto;

import com.emersun.rashin.collections.Permission;
import com.emersun.rashin.collections.Role;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

public class RoleDto extends AbstractBaseEntityDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private Set<Permission> permissions = new HashSet<>();

    public RoleDto(@NotEmpty String name, @NotEmpty Set<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public RoleDto(Role role) {
        super.setId(role.getId());
        this.name = role.getName();
        this.permissions = role.getPermissions();
    }

    public RoleDto() {
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
