import com.google.protobuf.gradle.*

plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.8.16"
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // grpc
    implementation("io.grpc:grpc-protobuf:1.36.2")
    implementation("io.grpc:grpc-stub:1.36.2")
    implementation("io.grpc:grpc-kotlin-stub:1.1.0")
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

    generatedFilesBaseDir = "$projectDir/src"

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.doFirst {
                delete("$generatedFilesBaseDir/main/grpc")
                delete("$generatedFilesBaseDir/main/grpckt")
                delete("$generatedFilesBaseDir/main/java")
            }
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
                "$projectDir/src/main/grpc",
                "$projectDir/src/main/grpckt",
                "$projectDir/src/main/java",
            )
        }
    }
}