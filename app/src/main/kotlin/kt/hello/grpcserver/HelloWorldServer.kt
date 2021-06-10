package kt.hello.grpcserver

import kt.hello.libs.grpc.server.BasicGrpcServer
import kt.hello.libs.grpc.server.Configure
import kt.hello.proto.GreeterGrpcKt

abstract class HelloWorldLogic : GreeterGrpcKt.GreeterCoroutineImplBase()

public class EnvImpl(override val grpcServerPort: Int) : Configure {
    companion object Loader {
        private const val GRPC_SERVER_PORT = "GRPC_SERVER_PORT"
        fun load(): Configure {
            return EnvImpl(
                grpcServerPort = System.getenv(Loader.GRPC_SERVER_PORT)?.toInt() ?: 50051,
            )
        }
    }
}

// https://github.com/grpc/grpc-kotlin/blob/master/examples/server/src/main/kotlin/io/grpc/examples/helloworld/HelloWorldServer.kt
class HelloWorldServer(
    private val helloWorldLogic: HelloWorldLogic,
    private val env: Configure,
) : BasicGrpcServer(helloWorldLogic, env)