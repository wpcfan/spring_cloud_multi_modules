plugins {
    id("java")
    id("io.freefair.lombok") version "8.7.1"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("com.fangxiaoer:spring-boot-starter-fangxiaoer-common:1.0.0")
    implementation("org.passay:passay:1.6.4")
    implementation("de.taimos:totp:1.0")
    implementation("com.aliyun:dysmsapi20170525:3.0.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("com.h2database:h2")
}
