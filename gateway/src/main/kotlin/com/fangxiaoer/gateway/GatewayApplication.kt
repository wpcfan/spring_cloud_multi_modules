package com.fangxiaoer.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@SpringBootApplication
open class GatewayApplication {
	// tag::fallback[]
	@RequestMapping("/fallback")
	fun fallback(): Mono<String> {
		return Mono.just("fallback")
	} // end::fallback[]
}

fun main(args: Array<String>) {
	runApplication<GatewayApplication>(*args)
}
