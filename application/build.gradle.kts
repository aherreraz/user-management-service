plugins {
    java
    id("io.freefair.lombok") version "8.11"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    // Module Dependencies
    implementation(project(":domain"))

    // Spring
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.5.14"))
    implementation("org.springframework:spring-context")
    implementation("org.hibernate.validator:hibernate-validator")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Mapstruct
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}