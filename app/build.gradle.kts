import com.google.protobuf.gradle.*

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
    id("com.google.protobuf") version "0.8.16"
    application
}

repositories {
    mavenCentral()
}

dependencies {
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
    implementation("io.grpc:grpc-stub:1.36.2")
    implementation("io.grpc:grpc-kotlin-stub:1.1.0")
}

application {
    mainClass.set("kt.hello.AppKt")
}

protobuf {
    protoc {
        if (osdetector.os == "osx" && osdetector.arch == "aarch_64") {
            logger.warn("(osx-aarch_64): using `protoc` which is in \$PATH")
        } else {
            artifact = "com.google.protobuf:protoc:3.8.0"
        }
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.37.0:osx-x86_64"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.1.0:jdk7@jar"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                "build/generated/source/proto/main/grpc",
                "build/generated/source/proto/main/grpckt",
                "build/generated/source/proto/main/java",
            )
        }
    }
}