package kt.hello

import kt.hello.grpcserver.EnvImpl
import kt.hello.grpcserver.HelloWorldLogic
import kt.hello.grpcserver.HelloWorldServer
import kt.hello.logic.HelloWorldLogicImpl
import org.koin.dsl.koinApplication
import org.koin.dsl.module

val appMod = module {
    single { EnvImpl.load() }
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
