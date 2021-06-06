package kt.hello.service

import io.grpc.Server
import io.grpc.ServerBuilder
import kt.hello.proto.GreeterGrpcKt
import kt.hello.proto.HelloReply
import kt.hello.proto.HelloRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger(HelloWorldServer::class.java)

// https://github.com/grpc/grpc-kotlin/blob/master/examples/server/src/main/kotlin/io/grpc/examples/helloworld/HelloWorldServer.kt
class HelloWorldServer(private val port: Int) {
    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(HelloWorldService())
        .build()

    fun start() {
        server.start()
        logger.info("gRPC Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                logger.info("*** shutting down gRPC server since JVM is shutting down")
                this@HelloWorldServer.stop()
                logger.info("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    private class HelloWorldService : GreeterGrpcKt.GreeterCoroutineImplBase() {
        override suspend fun sayHello(request: HelloRequest) = HelloReply
            .newBuilder()
            .setMessage("Hello ${request.name}")
            .build()
    }
}
