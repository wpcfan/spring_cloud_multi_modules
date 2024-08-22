package com.fangxiaoer.cms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;
    private String provider;
}
