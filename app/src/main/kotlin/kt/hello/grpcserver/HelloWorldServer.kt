package kt.hello.grpcserver

import io.grpc.Server
import io.grpc.ServerBuilder
import kt.hello.proto.GreeterGrpcKt
import org.koin.core.component.KoinComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger(HelloWorldServer::class.java)

interface Env {
    val grpcServerPort: Int
}

abstract class HelloWorldService : GreeterGrpcKt.GreeterCoroutineImplBase()

// https://github.com/grpc/grpc-kotlin/blob/master/examples/server/src/main/kotlin/io/grpc/examples/helloworld/HelloWorldServer.kt
class HelloWorldServer(
    private val helloWorldService: HelloWorldService,
    private val env: Env
) : BasicGrpcServer(helloWorldService, env)