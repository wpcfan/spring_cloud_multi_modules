package com.fangxiaoer.authserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private Sms sms;

    @Getter
    @Setter
    public static class Sms {
        private String accessKeyId;
        private String accessKeySecret;
        private String endpoint;
        private String provider;
        private String templateCode;
        private String signName;
    }
}
