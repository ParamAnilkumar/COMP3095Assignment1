plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "ca.gbc"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-api
    testImplementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.6.0")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:3.1.2")
    implementation("org.springframework.kafka:spring-kafka:3.3.0")
// https://mvnrepository.com/artifact/org.springframework.kafka/spring-kafka-test
    testImplementation("org.springframework.kafka:spring-kafka-test:3.3.0")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
