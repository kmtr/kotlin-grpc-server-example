package kt.hello

import kt.hello.grpcserver.Env
import kt.hello.grpcserver.HelloWorldLogic
import kt.hello.grpcserver.HelloWorldServer
import kt.hello.logic.HelloWorldLogicImpl
import org.koin.dsl.koinApplication
import org.koin.dsl.module

val appMod = module {
    single<Env> { EnvImpl.Loader.load() }
    single<HelloWorldLogic> { HelloWorldLogicImpl() }
    single { HelloWorldServer(get(), get()) }
    single { App(get()) }
}

class App(private val server: HelloWorldServer) {

    fun run() {
        server.start()
        server.blockUntilShutdown()
    }
}

fun main() {
    val koinApp = koinApplication() {
        modules(appMod)
    }
    koinApp.koin.get<App>().run()
}

public class EnvImpl(override val grpcServerPort: Int) : Env {
    companion object Loader {
        private const val GRPC_SERVER_PORT = "GRPC_SERVER_PORT"
        fun load(): Env {
            return EnvImpl(
                grpcServerPort = System.getenv(Loader.GRPC_SERVER_PORT)?.toInt() ?: 50051,
            )
        }
    }
}
