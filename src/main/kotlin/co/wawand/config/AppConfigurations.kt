package co.wawand.config

import co.wawand.data.db.DatabaseFactory
import co.wawand.di.RepositoryProvider
import co.wawand.routes.auth.authRoutes
import co.wawand.routes.story.storyRoutes
import co.wawand.routes.user.userRoutes
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun configureDatabase() {
    DatabaseFactory.init()
}

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        jackson()
    }
}

fun Application.configureRouting() {
    authRoutes(RepositoryProvider.provideAuthRepository())
    userRoutes(RepositoryProvider.provideUserRepository())
    storyRoutes(RepositoryProvider.provideStoryRepository())
}