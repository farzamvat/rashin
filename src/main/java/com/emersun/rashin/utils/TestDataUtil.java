package com.emersun.rashin.utils;

import com.emersun.rashin.collections.Permission;
import com.emersun.rashin.dto.RoleDto;
import com.emersun.rashin.panel.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TestDataUtil {
    @Autowired
    private RoleService roleService;

    public RoleDto createRole(String name, Set<Permission> permissions) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(name);
        roleDto.setPermissions(permissions);
        return roleService.create(roleDto).block();
    }
}
