package com.fangxiaoer.gateway

import com.github.tomakehurst.wiremock.client.WireMock.*
import dasniko.testcontainers.keycloak.KeycloakContainer
import junit.framework.TestCase.assertNotNull
import org.apache.http.client.utils.URIBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.JacksonJsonParser
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.testcontainers.junit.jupiter.Container
import java.net.URI
import java.net.URISyntaxException

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["httpbin=http://localhost:\${wiremock.server.port}"]
)
@org.testcontainers.junit.jupiter.Testcontainers
@AutoConfigureWireMock(port = 0)
class GatewayApplicationTests {
    var accessToken: String? = null

    @Autowired
    private lateinit var webClient: WebTestClient

    companion object {
        @Container
        @JvmStatic
        var keycloak: KeycloakContainer = KeycloakContainer("quay.io/keycloak/keycloak:25.0.4")
            .withRealmImportFile("realm-export.json")
            .withExposedPorts(8080,9000)

        private lateinit var authServerUrl: String

        @JvmStatic
        @BeforeAll
        fun setup() {
            keycloak.start()
            // 获取暴露端口的映射
            val mappedPort = keycloak.getMappedPort(8080)
            authServerUrl = "http://localhost:$mappedPort" + keycloak.authServerUrl.substringAfter("localhost:8080")
        }

        @JvmStatic
        @DynamicPropertySource
        fun registerResourceServerIssuerProperty(registry: DynamicPropertyRegistry) {
            registry.add("spring.security.oauth2.client.provider.keycloak.issuer-uri") {
                "$authServerUrl/realms/demo"
            }
            registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri") {
                "$authServerUrl/realms/demo/protocol/openid-connect/certs"
            }
            registry.add("spring.cloud.gateway.routes[0].uri") { "http://localhost:8060" }
            registry.add("spring.cloud.gateway.routes[0].id") { "callme-service" }
            registry.add("spring.cloud.gateway.routes[0].predicates[0]") { "Path=/callme/**" }
        }
    }

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

    @Test
    @Order(1)
    fun shouldBeRedirectedToLoginPage() {
        webClient.get().uri("/callme/ping")
            .exchange()
            .expectStatus().is3xxRedirection()
    }

    @Test
    @Order(2)
    @Throws(URISyntaxException::class)
    fun shouldObtainAccessToken() {
        val authorizationURI: URI =
            URIBuilder("$authServerUrl/realms/demo/protocol/openid-connect/token").build()
        val webclient = WebClient.builder().build()
        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
        formData.put("grant_type", listOf("password"))
        formData.put("client_id", listOf("spring-with-test-scope"))
        formData.put("username", listOf("spring"))
        formData.put("password", listOf("Spring_123"))

        val result: String? = webclient.post()
            .uri(authorizationURI)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono<String>(String::class.java)
            .block()
        val jsonParser = JacksonJsonParser()
        accessToken = jsonParser.parseMap(result)["access_token"]
            .toString()
        assertNotNull(accessToken)
    }

    @Test
    @Order(3)
    fun shouldReturnToken() {
        webClient.get().uri("/callme/ping")
            .header("Authorization", "Bearer $accessToken")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(String::class.java).isEqualTo("Hello!")
    }
}

