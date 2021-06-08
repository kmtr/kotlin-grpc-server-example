package kt.hello.grpcserver

import kt.hello.proto.GreeterGrpcKt

interface Env {
    val grpcServerPort: Int
}

abstract class HelloWorldService : GreeterGrpcKt.GreeterCoroutineImplBase()

// https://github.com/grpc/grpc-kotlin/blob/master/examples/server/src/main/kotlin/io/grpc/examples/helloworld/HelloWorldServer.kt
class HelloWorldServer(
    private val helloWorldService: HelloWorldService,
    private val env: Env
) : BasicGrpcServer(helloWorldService, env)