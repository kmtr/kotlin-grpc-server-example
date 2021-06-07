package kt.hello.grpcserver

import io.grpc.BindableService
import io.grpc.Server
import io.grpc.ServerBuilder

abstract class BasicGrpcServer(private val service: BindableService, private val env: Env) {

    private val server: Server = ServerBuilder
        .forPort(env.grpcServerPort)
        .addService(service)
        .build()

    fun start() {
        server.start()
        logger.info("gRPC Server started, listening on ${env.grpcServerPort}")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                logger.info("*** shutting down gRPC server since JVM is shutting down")
                this@BasicGrpcServer.stop()
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