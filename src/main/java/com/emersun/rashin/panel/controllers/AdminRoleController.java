package com.emersun.rashin.panel.controllers;

import com.emersun.rashin.dto.ResultDto;
import com.emersun.rashin.dto.RoleDto;
import com.emersun.rashin.panel.services.RoleService;
import com.emersun.rashin.security.AuthorizedUser;
import com.emersun.rashin.security.annotations.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${api.base.url}" + "/admin/roles")
public class AdminRoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public Mono<ResponseEntity<RoleDto>> create(@RequestBody @Validated RoleDto roleDto, @CurrentUser AuthorizedUser authorizedUser) {
        return roleService.create(roleDto)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body);
    }

    @PutMapping
    public Mono<ResponseEntity<RoleDto>> update(@RequestBody @Validated RoleDto roleDto) {
        return roleService.update(roleDto)
                .map(ResponseEntity.accepted()::body);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ResultDto>> delete(@PathVariable String id) {
        return roleService.delete(id)
                .map(ResponseEntity.accepted()::body);
    }
}
