
import config.module
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module, host = "127.0.0.1").start(true)
}
