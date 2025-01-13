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
    implementation(project(":domain"))
    implementation("org.hibernate.validator:hibernate-validator:6.2.3.Final")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}