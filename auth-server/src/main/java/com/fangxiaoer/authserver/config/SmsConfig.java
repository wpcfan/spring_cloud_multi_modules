package com.fangxiaoer.authserver.config;

import com.aliyun.teaopenapi.models.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SmsConfig {

    private final AppConfig appConfig;

    @Bean
    public com.aliyun.dysmsapi20170525.Client aliSmsClient() throws Exception {
        if (!"ali".equals(appConfig.getSms().getProvider())) {
            throw new Exception("Sms provider not supported");
        }
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(appConfig.getSms().getAccessKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(appConfig.getSms().getAccessKeySecret());
        // 访问的域名
        config.endpoint = appConfig.getSms().getEndpoint();

        return new com.aliyun.dysmsapi20170525.Client(config);
    }
}
