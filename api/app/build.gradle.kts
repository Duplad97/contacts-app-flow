plugins {
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.4"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.2.4")
    implementation("io.ktor:ktor-server-netty-jvm:2.2.4")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.2.4")
    implementation("io.ktor:ktor-serialization-gson-jvm:2.2.4")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("io.ktor:ktor-server-core:2.x.x")
    implementation("io.ktor:ktor-server-cors:2.x.x")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.2.4")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")
}

application {
    mainClass.set("org.example.AppKt")
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}