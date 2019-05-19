package com.emersun.rashin.security;

import com.emersun.rashin.exceptions.UnauthorizedException;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static io.vavr.API.*;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private final static Logger logger = LoggerFactory.getLogger(SecurityContextRepository.class);
    @Autowired
    private AdminAuthenticationManager authenticationManager;
    @Value("${api.base.url}")
    private String baseUrl;
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.fromSupplier(() -> exchange.getRequest())
                .map(request -> Option.of(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).getOrElse(() -> "invalid"))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .switchIfEmpty(Mono.error(new UnauthorizedException("unauthorized")))
                .log("log header checked")
                .flatMap(authHeader -> Match(exchange.getRequest().getPath().toString()).of(
                        Case($(path -> path.contains(baseUrl + "/admin")), () ->
                                        Mono.fromSupplier(() -> authHeader.substring(7))
                                                .log("Log header filtered by Bearer token type")
                                        .map(authToken -> new UsernamePasswordAuthenticationToken(authToken,authToken))
                                        .flatMap(authenticationManager::authenticate)
                        ),
                        Case($(),() -> Mono.error(new UnauthorizedException("")))
                ))
                .doOnError(throwable -> logger.error("Exception in SecurityContextRepository class exception is ",throwable))
                .map(SecurityContextImpl::new);
    }
}
