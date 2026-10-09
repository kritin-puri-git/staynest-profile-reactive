package com.example.staynest.profile.reactive.security.filter;

import com.example.staynest.profile.reactive.security.authentication.PublicIdAuthentication;
import com.example.staynest.profile.reactive.security.enums.JwtAudience;
import com.example.staynest.profile.reactive.validation.Validation;
import io.github.kritin_puri_git.jwt.model.TokenClaims;
import io.github.kritin_puri_git.jwt.service.JwtService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.net.InetSocketAddress;

@Component
public class AccessTokenWebFilter implements WebFilter {

    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final JwtService jwtService;

    public AccessTokenWebFilter(
            JwtService jwtService
    ){
        this.jwtService = jwtService;
    }

    @Override
    public @NonNull Mono<Void> filter(
            @NonNull ServerWebExchange exchange,
            @NonNull WebFilterChain chain
    ) {

        return Mono.defer(()->{

            ServerHttpRequest request = exchange.getRequest();

            if(request.getURI().getPath().startsWith("/v1/profile/search/user"))
                return chain.filter(exchange);

            String ip = getIp(request);
            Validation.validate(ip, "ip", CLASS_NAME);

            String accessToken = getAccessToken(request);

            TokenClaims tokenClaims = this.jwtService.verify(accessToken)
                    .orElseThrow(()-> new BadCredentialsException("Invalid AccessToken"));


            String publicId = tokenClaims.subject();

            if(!tokenClaims.audience().contains(JwtAudience.STAYNEST_PROFILE.name()))
                throw new BadCredentialsException("Invalid AccessToken");

            PublicIdAuthentication authentication = new PublicIdAuthentication(publicId);

            Context context = ReactiveSecurityContextHolder.withAuthentication(authentication);


            return chain.filter(exchange).contextWrite(context);
        });
    }

    private String getAccessToken(
            ServerHttpRequest request
    ){
        String authorization = request.getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        Validation.validate(authorization, "authorization", CLASS_NAME);

        if (!authorization.startsWith("Bearer ")) {
            throw new BadCredentialsException("Invalid Authorization header");
        }

        String accessToken = authorization.substring(7);

        Validation.validate(accessToken, "accessToken", CLASS_NAME);

        return accessToken;

    }

    private String getIp(
            ServerHttpRequest request
    ){
        InetSocketAddress remoteAddress = request.getRemoteAddress();

        if (remoteAddress == null || remoteAddress.isUnresolved()
                || remoteAddress.getAddress() == null) {
            return null;
        }

        return remoteAddress.getAddress().getHostAddress();
    }

}