package com.twigcodes.authserver.services.mfa;

import com.twigcodes.authserver.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MfaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TotpService totpService;

    public MfaAuthenticationFilter(TotpService totpService) {
        this.totpService = totpService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String mfaCode = request.getParameter("mfaCode");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);

        Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);

        User userDetails = (User) authentication.getPrincipal();

        if (userDetails.isMfaEnabled()) {
            if (mfaCode == null || !totpService.validateTotp(userDetails.getTotpSecret(), mfaCode)) {
                throw new AuthenticationException("MFA code is invalid or missing.") {};
            }
        }

        return authentication;
    }
}
