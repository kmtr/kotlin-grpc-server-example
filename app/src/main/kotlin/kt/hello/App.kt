/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package kt.hello

import kt.hello.service.HelloWorldServer

public class Env(val grpcServerPort: Int) {
    companion object Loader {
        private const val GRPC_SERVER_PORT = "GRPC_SERVER_PORT"
        fun load(): Env {
            return Env(
                grpcServerPort = System.getenv(Env.GRPC_SERVER_PORT)?.toInt() ?: 50051,
            )
        }
    }
}

class App {
    val greeting: String
        get() {
            return "Hello, gRPC server!"
        }
}

fun main() {
    println(
        App().greeting
    )
    val env = Env.Loader.load()
    val server = HelloWorldServer(env.grpcServerPort)
    server.start()
    server.blockUntilShutdown()
}
