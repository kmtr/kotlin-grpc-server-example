package kt.hello.grpcserver

import kt.hello.libs.grpc.server.BasicGrpcServer
import kt.hello.libs.grpc.server.Configuration
import kt.hello.proto.GreeterGrpcKt

abstract class HelloWorldLogic : GreeterGrpcKt.GreeterCoroutineImplBase()

public class EnvImpl(override val grpcServerPort: Int) : Configuration {
    companion object Loader {
        private const val GRPC_SERVER_PORT = "GRPC_SERVER_PORT"
        fun load(): Configuration {
            return EnvImpl(
                grpcServerPort = System.getenv(Loader.GRPC_SERVER_PORT)?.toInt() ?: 50051,
            )
        }
    }
}

// https://github.com/grpc/grpc-kotlin/blob/master/examples/server/src/main/kotlin/io/grpc/examples/helloworld/HelloWorldServer.kt
class HelloWorldServer(
    private val helloWorldLogic: HelloWorldLogic,
    private val env: Configuration,
) : BasicGrpcServer(helloWorldLogic, env)