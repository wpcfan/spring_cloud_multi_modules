package com.twigcodes.serviceb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
open class ServiceBApplication

fun main(args: Array<String>) {
    runApplication<ServiceBApplication>(*args)
}