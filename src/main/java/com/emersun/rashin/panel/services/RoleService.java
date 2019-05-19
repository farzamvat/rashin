package com.emersun.rashin.panel.services;

import com.emersun.rashin.dto.ResultDto;
import com.emersun.rashin.dto.RoleDto;
import com.emersun.rashin.security.AuthorizedUser;
import reactor.core.publisher.Mono;

public interface RoleService {

    Mono<RoleDto> create(RoleDto roleDto);

    Mono<ResultDto> delete(String id);

    Mono<RoleDto> update(RoleDto roleDto);
}
