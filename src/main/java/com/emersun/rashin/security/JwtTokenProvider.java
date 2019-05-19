package com.emersun.rashin.security;

import com.emersun.rashin.configs.Constants;
import com.emersun.rashin.exceptions.UnauthorizedException;
import com.emersun.rashin.security.models.AuthResponse;
import com.emersun.rashin.utils.Messages;
import com.emersun.rashin.utils.messages.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {
    @Autowired
    private Messages messages;
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.access.expire-length}")
    private Long accessTokenValidityInDays;
    @Value("${security.jwt.token.refresh.expire-length}")
    private Long refreshTokenValidityInDays;
    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    public AuthResponse createToken(String username, Optional<String> optionalRefresh) {
        AuthResponse authResponse = new AuthResponse();
        Long expire = new Date().getTime();
        authResponse.setAccessTokenExpireTime(new Date(
                LocalDateTime.now(ZoneId.of("Asia/Tehran"))
                        .plusDays(accessTokenValidityInDays)
                        .atZone(ZoneId.of("Asia/Tehran"))
                        .toInstant().toEpochMilli()
        ).getTime());
        authResponse.setRefreshTokenExpireTime(new Date(
                LocalDateTime.now(ZoneId.of("Asia/Tehran"))
                        .plusDays(refreshTokenValidityInDays)
                        .atZone(ZoneId.of("Asia/Tehran"))
                        .toInstant().toEpochMilli()
        ).getTime());
        authResponse.setAccessToken(createAccessToken(username));
        Option.ofOptional(optionalRefresh)
                .peek(refresh -> authResponse.setRefreshToken(refresh))
                .onEmpty(() -> authResponse.setRefreshToken(createRefreshToken(username)));
        return authResponse;
    }

    public String createAccessToken(String username) {
        return createToken(username, Constants.ACCESS_TOKEN_AUDIENCE,new Date(
                LocalDateTime.now(ZoneId.of("Asia/Tehran"))
                        .plusDays(accessTokenValidityInDays)
                        .atZone(ZoneId.of("Asia/Tehran"))
                        .toInstant().toEpochMilli()
        ).getTime());
    }

    public String createRefreshToken(String username) {
        return createToken(username, Constants.REFRESH_TOKEN_AUDIENCE, new Date(
                LocalDateTime.now(ZoneId.of("Asia/Tehran"))
                        .plusDays(refreshTokenValidityInDays)
                        .atZone(ZoneId.of("Asia/Tehran"))
                        .toInstant().toEpochMilli()
        ).getTime());
    }

    private String createToken(String username,String audience,Long expire) {
        Claims claims = Jwts.claims()
                .setSubject(username)
                .setAudience(audience);
        Date now = new Date();
        Date validity = new Date(expire);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    public boolean validateToken(String token,String audience) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            if(claims.getAudience().equals(audience))
                return true;
            else
                throw new UnauthorizedException(messages.get(Response.ACCESS_DENIED));
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Exception : {}",e);
            throw new UnauthorizedException(messages.get(Response.ACCESS_DENIED));
        }
    }
}
