package com.twigcodes.authserver.services.smsmfa;

import com.twigcodes.authserver.services.sms.SmsCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UsernamePasswordAndSmsMfaAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final SmsCodeService smsCodeService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Extract the principal (username), credentials (password), and the SMS code
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String smsCode = ((UsernamePasswordAndSmsMfaAuthenticationToken) authentication).getSmsCode();

        // Load the user details using the username
        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("User details retrieval failed", e);
        }

        if (user == null || !user.isAccountNonLocked()) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Validate the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Validate the SMS code
        if (!smsCodeService.validateCode(username, smsCode)) {
            throw new BadCredentialsException("Invalid SMS code");
        }

        // If everything is valid, create a new authenticated token
        return new UsernamePasswordAndSmsMfaAuthenticationToken(user, null, smsCode, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAndSmsMfaAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
