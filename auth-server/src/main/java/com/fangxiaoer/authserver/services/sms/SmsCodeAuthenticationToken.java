package com.fangxiaoer.authserver.services.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private Object credentials;

    public SmsCodeAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
