plugins {
    application
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation(project(":domain"))
    implementation(project(":application"))
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}