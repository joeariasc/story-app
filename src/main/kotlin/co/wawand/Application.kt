package co.wawand

import co.wawand.config.configureContentNegotiation
import co.wawand.config.configureDatabase
import co.wawand.config.configureRouting
import co.wawand.config.configureStatusPages
import co.wawand.security.configSecurity
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDatabase()
    configureContentNegotiation()
    configureStatusPages()
    configSecurity()
    configureRouting()
}
