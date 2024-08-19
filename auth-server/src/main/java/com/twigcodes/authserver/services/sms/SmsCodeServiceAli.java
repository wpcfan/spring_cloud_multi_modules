package com.twigcodes.authserver.services.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teautil.models.RuntimeOptions;
import com.twigcodes.authserver.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class SmsCodeServiceAli implements SmsCodeService {
    private final AppConfig appConfig;
    private final Client client;
    private final StringRedisTemplate redisTemplate;

    private static final String SMS_CODE_PREFIX = "sms:code:";

    @Override
    public void sendSmsCode(String phone) {
        String code = generateCode(6);
        // 将验证码存储到 Redis，设置5分钟过期时间
        redisTemplate.opsForValue().set(SMS_CODE_PREFIX + phone, code, 5, TimeUnit.MINUTES);

        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(appConfig.getSms().getSignName())
                .setTemplateCode(appConfig.getSms().getTemplateCode())
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            client.sendSmsWithOptions(request, new RuntimeOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validateCode(String phone, String code) {
        String key = SMS_CODE_PREFIX + phone;
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String storedCode = ops.get(key);

        if (storedCode != null && storedCode.equals(code)) {
            // 验证通过后删除验证码
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    private String generateCode(int length) {
        return String.format("%0" + length + "d", (int) (Math.random() * Math.pow(10, length)));
    }
}