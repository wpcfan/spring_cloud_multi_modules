package com.fangxiaoer.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RouteConfig {
    @Bean
    open fun myRoutes(builder: RouteLocatorBuilder, uriConfiguration: UriConfiguration): RouteLocator {
        val httpUri: String = uriConfiguration.httpbin;
        return builder.routes()
            .route { p ->
                p.path("/get")
                    .filters { f -> f.addRequestHeader("Hello", "World") }
                    .uri(httpUri)
            }
            .route { p ->
                p.host("*.circuitbreaker.com")
                    .filters { f -> f.circuitBreaker { h -> h.setName("mycmd").setFallbackUri("forward:/fallback") } }
                    .uri(httpUri)
            }
            .build()
    }
}
