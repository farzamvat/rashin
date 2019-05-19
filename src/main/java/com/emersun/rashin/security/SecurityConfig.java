package com.emersun.rashin.security;

import com.emersun.rashin.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Value("${api.base.url}")
    private String baseUrl;

    @Autowired
    private AdminAuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http.cors().disable().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/login").permitAll()
                .pathMatchers(baseUrl + "/public/**").permitAll()
                .pathMatchers("/swagger-ui.html").permitAll()
                .anyExchange().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler((exchange, denied) -> {
                    logger.error("access denied handler '{}'",denied);
                    return Mono.error(denied);
                })
                .authenticationEntryPoint((exchange, e) -> {
                    logger.error("authentication entry point '{}'",e);
                    return Mono.error(new UnauthorizedException());
                })
                .and().build();

    }
}
