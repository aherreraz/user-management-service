plugins {
    application
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation(project(":domain"))
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}