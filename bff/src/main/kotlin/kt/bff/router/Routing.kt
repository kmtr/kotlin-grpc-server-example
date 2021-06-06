package kt.bff.router

import io.ktor.routing.*;
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("This is BFF")
        }
    }
}