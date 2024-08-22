package com.fangxiaoer.serviceb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@SpringBootApplication
@EnableDiscoveryClient
class ServiceBApplication {

    @GetMapping("/hello/from/b")
    fun hello(): String {
        return "Hello from B"
    }

    @GetMapping("/hello/from/b/calling/a")
    fun helloCallingA(restTemplate: RestTemplate): String {
        return "Hello from B calling A: " + restTemplate.getForObject("http://localhost:9000/service-a/hello/from/a", String::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<ServiceBApplication>(*args)
}
