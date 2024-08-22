package com.fangxiaoer.gateway

import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.core.Ordered
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Component
class AuditFilter : GlobalFilter, Ordered {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return ReactiveSecurityContextHolder.getContext()
            .map { it.authentication.principal as UserDetails }
            .flatMap { userDetails ->
                val username = userDetails.username
                val requestPath = exchange.request.path.toString()
                val method = exchange.request.method.name()
                val timestamp = LocalDateTime.now()

                // 记录请求审计日志
                println("Request Audit Log: $method $requestPath by user $username at $timestamp")

                chain.filter(exchange).then(Mono.fromRunnable<Void> {
                    val statusCode = exchange.response.statusCode?.value()
                    val responseTime = LocalDateTime.now()

                    // 记录响应审计日志
                    println("Response Audit Log: Status $statusCode by user $username at $responseTime")
                })
            }
            .then(chain.filter(exchange)) // 继续处理请求
    }

    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }
}
