package com.twigcodes.servicea

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ServiceAApplication

fun main(args: Array<String>) {
    runApplication<ServiceAApplication>(*args)
}