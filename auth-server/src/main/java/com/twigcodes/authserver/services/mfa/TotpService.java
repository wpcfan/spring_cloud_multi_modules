package com.twigcodes.authserver.services.mfa;

import org.springframework.stereotype.Service;
import de.taimos.totp.TOTP;

@Service
public class TotpService {

    // 生成一个 TOTP 密码
    public String generateTotp(String secret) {
        return TOTP.getOTP(secret);
    }

    // 验证 TOTP 密码是否有效
    public boolean validateTotp(String secret, String code) {
        String generatedCode = generateTotp(secret);
        return generatedCode.equals(code);
    }
}
