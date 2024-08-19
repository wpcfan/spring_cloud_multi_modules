package com.twigcodes.authserver.services.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    private final SmsCodeService smsCodeService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobile = authentication.getName();
        String smsCode = (String) authentication.getCredentials();

        if (!smsCodeService.validateCode(mobile, smsCode)) {
            throw new BadCredentialsException("Invalid SMS Code");
        }

        // 加载用户详细信息
        UserDetails userDetails = loadUserByMobile(mobile);

        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }

        throw new BadCredentialsException("User not found");
    }

    private UserDetails loadUserByMobile(String mobile) {
        // 实现你的逻辑通过手机号加载用户
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

