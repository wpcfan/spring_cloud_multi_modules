package com.fangxiaoer.authserver.services.mfa;

import com.fangxiaoer.authserver.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class MfaAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TotpService totpService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        String mfaCode = (String) authentication.getDetails(); // 从 Details 中获取 MFA 代码

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 验证密码
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        // 如果用户启用了 MFA，验证 TOTP 代码
        User customUserDetails = (User) userDetails;
        if (customUserDetails.isMfaEnabled()) {
            if (mfaCode == null || !totpService.validateTotp(customUserDetails.getTotpSecret(), mfaCode)) {
                throw new BadCredentialsException("Invalid MFA code.");
            }
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

