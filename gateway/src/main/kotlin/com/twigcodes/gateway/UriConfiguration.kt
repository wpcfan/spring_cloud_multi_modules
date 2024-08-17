package com.twigcodes.gateway

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties
open class UriConfiguration {
    var httpbin: String = "http://httpbin.org:80"
}
