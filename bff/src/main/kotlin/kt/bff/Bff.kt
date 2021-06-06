package kt.bff

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kt.bff.router.configureRouting
import kt.hello.proto.HelloReply
import kt.hello.proto.GreeterGrpc
import kt.hello.proto.GreeterGrpcKt

class Bff {
    val greeting: String
        get() {
            return "Hello BFF!"
        }
}

fun main() {
    println(
        Bff().greeting
    )
    embeddedServer(
        Netty, port = 8080, host = "127.0.0.1"
    ) {
        configureRouting()
    }.start(wait = true)
}
