package kt.bff

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kt.bff.router.configureRouting


public class Env(val serverPort: Int, val serverHost: String) {
    companion object Loader {
        private const val SERVER_PORT = "SERVER_PORT"
        private const val SERVER_HOST = "SERVER_HOST"
        fun load(): Env {
            return Env(
                serverPort = System.getenv(Env.SERVER_PORT)?.toInt() ?: 8080,
                serverHost = System.getenv(Env.SERVER_HOST) ?: "127.0.0.1",
            )
        }
    }
}


fun main() {
    val env = Env.Loader.load()
    embeddedServer(
        Netty, port = env.serverPort, host = env.serverHost,
    ) {
        configureRouting()
    }.start(wait = true)
}
