package com.twigcodes.authserver.services.smsmfa;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class UsernamePasswordAndSmsMfaAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;
    private String smsCode;

    // Constructor for authentication before verification
    public UsernamePasswordAndSmsMfaAuthenticationToken(Object principal, Object credentials, String smsCode) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.smsCode = smsCode;
        setAuthenticated(false);
    }

    // Constructor for authentication after verification
    public UsernamePasswordAndSmsMfaAuthenticationToken(Object principal, Object credentials, String smsCode,
                                                        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.smsCode = smsCode;
        setAuthenticated(true);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
