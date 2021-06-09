plugins {
    kotlin("jvm") version "1.5.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // project
    implementation(project(":app:proto"))

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.0-jre")

    // ktor
    implementation("io.ktor:ktor-server-core:1.6.0")
    implementation("io.ktor:ktor-server-netty:1.6.0")
    testImplementation("io.ktor:ktor-server-tests:1.6.0")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // grpc
    implementation("io.grpc:grpc-netty:1.36.2")
    implementation("io.grpc:grpc-protobuf:1.36.2")
    implementation("io.grpc:grpc-kotlin-stub:1.1.0")

    // log
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
}

application {
    mainClass.set("kt.bff.BffKt")
}
