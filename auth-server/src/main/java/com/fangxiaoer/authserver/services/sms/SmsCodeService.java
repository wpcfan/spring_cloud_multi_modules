package com.fangxiaoer.authserver.services.sms;

public interface SmsCodeService {
    void sendSmsCode(String phone);

    boolean validateCode(String phone, String code);
}
