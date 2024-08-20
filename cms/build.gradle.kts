val redissonVersion = "3.14.1"
val springDocVersion = "2.6.+"
val qiniuVersion = "7.13.+"

plugins {
    id("java")
    id("io.freefair.lombok") version "8.7.1"
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
    implementation("org.redisson:redisson-spring-boot-starter:$redissonVersion")
    implementation("com.qiniu:qiniu-java-sdk:$qiniuVersion")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.hamcrest:hamcrest-library")
    "developmentOnly"("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
}
