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

fun main() {
    val env = Env.Loader.load()
    val server = HelloWorldServer(env.grpcServerPort)
    server.start()
    server.blockUntilShutdown()
}
