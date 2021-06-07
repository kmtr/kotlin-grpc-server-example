package kt.hello

import kt.hello.grpcserver.Env
import kt.hello.grpcserver.HelloWorldServer
import kt.hello.grpcserver.HelloWorldService
import kt.hello.service.HelloWorldServiceImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appMod = module {
    single<Env> { EnvImpl.Loader.load() }
    single<HelloWorldService> { HelloWorldServiceImpl() }
    single { HelloWorldServer(get(), get()) }
}

class App : KoinComponent {
    private val server by inject<HelloWorldServer>()

    fun run() {
        server.start()
        server.blockUntilShutdown()
    }
}

fun main() {
    startKoin {
        modules(appMod)
    }
    App().run()
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
