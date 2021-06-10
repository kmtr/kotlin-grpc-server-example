package kt.hello.logic

import kt.hello.grpcserver.HelloWorldLogic
import kt.hello.proto.HelloReply
import kt.hello.proto.HelloRequest

class HelloWorldLogicImpl : HelloWorldLogic() {
    override suspend fun sayHello(request: HelloRequest) = HelloReply
        .newBuilder()
        .setMessage("Hello ${request.name}")
        .build()
}