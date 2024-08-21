val springDocVersion = "2.6.+"
val swaggerParserVersion = "2.1.22"
val swaggerCoreVersion = "2.2.22"
plugins {
    id("java")
    id("io.freefair.lombok") version "8.7.1"
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.swagger.core.v3:swagger-core:$swaggerCoreVersion")
    implementation("io.swagger.parser.v3:swagger-parser:$swaggerParserVersion")
    testImplementation("io.projectreactor:reactor-test")
}

