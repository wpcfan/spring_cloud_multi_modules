plugins {
    id("org.jetbrains.kotlin.plugin.spring") version "2.0.10"
}
apply(plugin = "org.jetbrains.kotlin.plugin.spring")
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.boot:spring-boot-starter-security")
}
