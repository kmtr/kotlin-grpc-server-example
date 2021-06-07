package kt.hello.service

import kt.hello.grpcserver.HelloWorldService
import kt.hello.proto.GreeterGrpcKt
import kt.hello.proto.HelloReply
import kt.hello.proto.HelloRequest

class HelloWorldServiceImpl : HelloWorldService() {
    override suspend fun sayHello(request: HelloRequest) = HelloReply
        .newBuilder()
        .setMessage("Hello ${request.name}")
        .build()
}