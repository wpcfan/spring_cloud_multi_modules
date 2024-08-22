package com.fangxiaoer.servicea

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
@EnableDiscoveryClient
class ServiceAApplication {
    @Value("\${server.port}")
    private val serverPort: String? = null
    @LoadBalanced
    @GetMapping("/hello/from/a")
    fun hello(): String {
        return "Hello from A" + if (serverPort != null) " on port " + serverPort else ""
    }
}

fun main(args: Array<String>) {
    runApplication<ServiceAApplication>(*args)
}
