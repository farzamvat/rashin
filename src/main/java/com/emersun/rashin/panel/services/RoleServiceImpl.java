package com.emersun.rashin.panel.services;

import com.emersun.rashin.collections.Role;
import com.emersun.rashin.dto.ResultDto;
import com.emersun.rashin.dto.RoleDto;
import com.emersun.rashin.exceptions.BadRequestException;
import com.emersun.rashin.repositories.RoleRepository;
import com.emersun.rashin.security.AuthorizedUser;
import com.emersun.rashin.utils.Messages;
import com.emersun.rashin.utils.messages.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private Messages messages;

    @Override
    public Mono<RoleDto> create(RoleDto roleDto) {
        return roleRepository.existsByName(roleDto.getName())
                .filter(result -> !result)
                .switchIfEmpty(Mono.error(new BadRequestException("A role with requested name already exists in the system")))
                .map(res -> new Role(roleDto))
                .flatMap(roleRepository::save)
                .map(RoleDto::new);
    }

    @Override
    public Mono<ResultDto> delete(String id) {
        return roleRepository.findById(id)
                .switchIfEmpty(Mono.error(new BadRequestException("Role not found")))
                .flatMap(role -> roleRepository.delete(role))
                .map(aVoid -> new ResultDto(messages.get(Response.SUCCESS)));
    }

    @Override
    public Mono<RoleDto> update(RoleDto roleDto) {
        return roleRepository.findById(roleDto.getId())
                .switchIfEmpty(Mono.error(new BadRequestException("Role not found")))
                .map(role -> role.update(roleDto))
                .flatMap(roleRepository::save)
                .map(RoleDto::new);
    }
}
