plugins {
    application
    java
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}