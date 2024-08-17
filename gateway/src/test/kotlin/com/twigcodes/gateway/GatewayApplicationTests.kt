package com.twigcodes.gateway

import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = ["httpbin=http://localhost:\${wiremock.server.port}"]
)
@AutoConfigureWireMock(port = 0)
class GatewayApplicationTests {

	@Autowired
	private lateinit var webClient: WebTestClient

	@Test
	fun testRoutesAndCircuitBreak() {
		// Stubs
		stubFor(get(urlEqualTo("/get"))
			.willReturn(aResponse()
				.withBody("""{"headers":{"Hello":"World"}}""")
				.withHeader("Content-Type", "application/json")))

		stubFor(get(urlEqualTo("/delay/3"))
			.willReturn(aResponse()
				.withBody("no fallback")
				.withFixedDelay(3000)))

		webClient.get().uri("/get")
			.exchange()
			.expectStatus().isOk
			.expectBody()
			.jsonPath("$.headers.Hello").isEqualTo("World")

		webClient.get().uri("/delay/3")
			.header("Host", "www.circuitbreaker.com")
			.exchange()
			.expectStatus().isOk
			.expectBody()
			.consumeWith { response ->
				assertThat(response.responseBody).isEqualTo("fallback".toByteArray())
			}
	}
}

