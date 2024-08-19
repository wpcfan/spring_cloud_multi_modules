package com.twigcodes.authserver.config;

import com.twigcodes.authserver.services.JpaUserDetailService;
import com.twigcodes.authserver.services.mfa.MfaAuthenticationFilter;
import com.twigcodes.authserver.services.mfa.MfaAuthenticationProvider;
import com.twigcodes.authserver.services.mfa.TotpService;
import com.twigcodes.authserver.services.sms.SmsCodeAuthenticationFilter;
import com.twigcodes.authserver.services.sms.SmsCodeAuthenticationProvider;
import com.twigcodes.authserver.services.sms.SmsCodeService;
import com.twigcodes.authserver.services.smsmfa.UsernamePasswordAndSmsMfaAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {

    private final SmsCodeService smsCodeService;
    private final TotpService totpService;
    private final JpaUserDetailService userDetailsService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations(),
                                PathRequest.toH2Console(),
                                antMatcher("/swagger-ui/**"),
                                antMatcher("/swagger-ui.html"),
                                antMatcher("/v3/api-docs/**"),
                                antMatcher("/assets"),
                                antMatcher("/login")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                )
                .rememberMe(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        // Disabling CSRF protection for the H2 Console
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")
                );

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SmsCodeAuthenticationFilter smsCodeAuthenticationFilter() {
        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public SmsCodeAuthenticationProvider smsCodeAuthenticationProvider() {
        return new SmsCodeAuthenticationProvider(smsCodeService);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProviders());
    }

    @Bean
    public MfaAuthenticationFilter mfaAuthenticationFilter() {
        MfaAuthenticationFilter filter = new MfaAuthenticationFilter(totpService);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public MfaAuthenticationProvider mfaAuthenticationProvider() {
        return new MfaAuthenticationProvider(
                userDetailsService,
                passwordEncoder(),
                totpService
        );
    }

    @Bean
    public UsernamePasswordAndSmsMfaAuthenticationProvider usernamePasswordAndSmsMfaAuthenticationProvider(PasswordEncoder passwordEncoder) {
        return new UsernamePasswordAndSmsMfaAuthenticationProvider(
                userDetailsService,
                passwordEncoder,
                smsCodeService
        );
    }

    private List<AuthenticationProvider> customAuthenticationProviders() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(smsCodeAuthenticationProvider());
        providers.add(mfaAuthenticationProvider());
        providers.add(usernamePasswordAndSmsMfaAuthenticationProvider(passwordEncoder()));
        return providers;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
