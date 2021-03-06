plugins {
    kotlin("jvm") version "1.5.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // project
    implementation(project(":libs:grpc"))
    implementation(project(":app:proto"))

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.0-jre")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // grpc
    implementation("io.grpc:grpc-netty:1.36.2")
    implementation("io.grpc:grpc-protobuf:1.36.2")
    implementation("io.grpc:grpc-stub:1.36.2")
    implementation("io.grpc:grpc-kotlin-stub:1.1.0")

    // log
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")

    // koin
    implementation("io.insert-koin:koin-core:3.0.2")
    testImplementation("io.insert-koin:koin-test:3.0.2")
}

application {
    mainClass.set("kt.hello.AppKt")
}
