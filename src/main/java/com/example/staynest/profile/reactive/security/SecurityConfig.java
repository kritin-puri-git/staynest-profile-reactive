package com.example.staynest.profile.reactive.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(
            ServerHttpSecurity http
    )throws Exception{

        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);

        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

//        http.csrf(
//                csrfSpec ->
//                        csrfSpec.csrfTokenRepository(
//                                new CookieServerCsrfTokenRepository()
//                        )
//        );

        http.authorizeExchange(
                exchange->
                        exchange.pathMatchers(
                                        "/v1/profile/search/user"
                        ).permitAll()
                                .anyExchange()
                                .authenticated()
        );

        return http.build();
    }


}