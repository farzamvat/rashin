package com.emersun.rashin.security;

import com.emersun.rashin.collections.Agent;
import com.emersun.rashin.security.models.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${security.jwt.token.secret-key}")
    private String secret;

    @Value("${security.jwt.token.access.expire-length}")
    private String accessTokenExpirationTime;

    @Value("${security.jwt.token.refresh.expire-length}")
    private String refreshTokenExpirationTime;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes())).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Mono<AuthResponse> generateToken(Agent agent) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", new Object[]{agent.getRole().getName()});
        Long accessTokenExpirationTimeLong = Long.parseLong(accessTokenExpirationTime);
        Long refreshTokenExpirationTimeLong = Long.parseLong(refreshTokenExpirationTime);
        final Date createdDate = new Date();
        final Date accessExpirationTime = new Date(createdDate.getTime() + accessTokenExpirationTimeLong * 1000);
        final Date refreshExpirationTime = new Date(createdDate.getTime() + refreshTokenExpirationTimeLong * 1000);

        return doGenerateToken(claims, agent.getUsername(),accessExpirationTime)
                .zipWith(doGenerateToken(claims, agent.getUsername(),refreshExpirationTime),(a, r) ->
                        new AuthResponse(a,r,accessExpirationTime.getTime(),refreshExpirationTime.getTime()));
    }

    public String doGenerateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", new Object[]{"ADMIN"});
        Long accessTokenExpirationTimeLong = Long.parseLong(accessTokenExpirationTime);
        Long refreshTokenExpirationTimeLong = Long.parseLong(refreshTokenExpirationTime);
        final Date createdDate = new Date();
        final Date accessExpirationTime = new Date(createdDate.getTime() + accessTokenExpirationTimeLong * 1000);
        final Date refreshExpirationTime = new Date(createdDate.getTime() + refreshTokenExpirationTimeLong * 1000);
        return Jwts.builder().setClaims(claims)
                .setSubject(username)
                .setExpiration(accessExpirationTime)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
    }

    private Mono<String> doGenerateToken(Map<String,Object> claims, String username,Date expirationDate) {
        return Mono.fromSupplier(() ->
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(username)
                        .setIssuedAt(new Date())
                        .setExpiration(expirationDate)
                        .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
                        .compact());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

