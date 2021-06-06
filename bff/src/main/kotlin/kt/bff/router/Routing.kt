package kt.bff.router

import io.grpc.ManagedChannelBuilder
import io.ktor.routing.*;
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import kt.bff.service.HelloWorldClient

fun Application.configureRouting() {
    routing {
        get("/") {
            val port = 50051
            val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()
            val client = HelloWorldClient(channel)
            val user = "world"
            val result = client.greet(user)

            call.respondText(result)
        }
    }
}