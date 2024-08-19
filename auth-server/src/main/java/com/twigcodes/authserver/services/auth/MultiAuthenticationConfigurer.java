package com.twigcodes.authserver.services.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

public class MultiAuthenticationConfigurer extends AbstractHttpConfigurer<MultiAuthenticationConfigurer, HttpSecurity> {

    private final List<AuthenticationProvider> authenticationProviders;

    public MultiAuthenticationConfigurer(List<AuthenticationProvider> authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        for (AuthenticationProvider provider : authenticationProviders) {
            http.authenticationProvider(provider);
        }
    }
}