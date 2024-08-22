package com.twigcodes.cms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cms")
public class CmsConfig {
    private String dateTimeFormat;
    private String dateFormat;
}
