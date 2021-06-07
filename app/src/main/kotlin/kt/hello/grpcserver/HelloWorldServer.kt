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
) : KoinComponent {

    private val server: Server = ServerBuilder
        .forPort(env.grpcServerPort)
        .addService(helloWorldService)
        .build()

    fun start() {
        server.start()
        logger.info("gRPC Server started, listening on ${env.grpcServerPort}")
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
}
