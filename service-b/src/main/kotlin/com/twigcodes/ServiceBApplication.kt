package com.twigcodes.serviceb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ServiceBApplication

fun main(args: Array<String>) {
    runApplication<ServiceBApplication>(*args)
}