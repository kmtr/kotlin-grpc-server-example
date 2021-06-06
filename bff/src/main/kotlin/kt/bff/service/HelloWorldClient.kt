package kt.bff.service

import io.grpc.ManagedChannel

import kt.hello.proto.GreeterGrpcKt.GreeterCoroutineStub
import kt.hello.proto.HelloRequest
import java.io.Closeable
import java.util.concurrent.TimeUnit

class HelloWorldClient(private val channel: ManagedChannel) : Closeable {
    private val stub: GreeterCoroutineStub = GreeterCoroutineStub(channel)

    suspend fun greet(name: String): String {
        val request = HelloRequest.newBuilder().setName(name).build()
        val response = stub.sayHello(request)
        return "Received: ${response.message}"
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}