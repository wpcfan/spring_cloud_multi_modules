plugins {
    id("java")
}
apply(plugin = "java")
dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("de.codecentric:spring-boot-admin-starter-server:3.3.3")
}