plugins {
    id("org.jetbrains.kotlin.plugin.spring") version "2.0.10"
}
apply(plugin = "org.jetbrains.kotlin.plugin.spring")
dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}