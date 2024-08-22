package com.fangxiaoer.gateway

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.*
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange { auth: AuthorizeExchangeSpec -> auth.anyExchange().authenticated() }
            .oauth2Login(Customizer.withDefaults())
            .oauth2ResourceServer { oauth2: OAuth2ResourceServerSpec -> oauth2.jwt(Customizer.withDefaults()) }
        http.csrf { csrf: CsrfSpec -> csrf.disable() }
        return http.build()
    }
}
