package com.emersun.rashin.security;

import com.emersun.rashin.exceptions.UnauthorizedException;
import com.emersun.rashin.panel.agent.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AdminAuthenticationManager implements ReactiveAuthenticationManager {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.fromSupplier(() -> authentication.getCredentials().toString())
                .filter(token -> token != null && jwtUtil.validateToken(token))
                .switchIfEmpty(Mono.error(new UnauthorizedException("unauthorized")))
                .map(jwtUtil::getUsernameFromToken)
                .flatMap(username -> agentRepository.findByUsername(username))
                .map(agent -> AuthorizedUser.builder()
                        .username(agent.getUsername())
                        .password(agent.getPassword())
                        .role(agent.getRole())
                        .build())
                .switchIfEmpty(Mono.error(new UnauthorizedException("unauthorized")))
                .map(authorizedUser -> new UsernamePasswordAuthenticationToken(authorizedUser,null,authorizedUser.getAuthorities()));
    }
}
