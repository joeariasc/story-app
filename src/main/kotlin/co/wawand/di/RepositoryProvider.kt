package co.wawand.di

import co.wawand.data.repository.auth.AuthRepositoryImpl
import co.wawand.data.repository.story.StoryRepository
import co.wawand.data.repository.story.StoryRepositoryImpl
import co.wawand.data.repository.user.UserRepository
import co.wawand.data.repository.user.UserRepositoryImpl
import co.wawand.data.service.auth.AuthServiceImpl
import co.wawand.data.service.story.StoryServiceImpl
import co.wawand.data.service.user.UserServiceImpl
import co.wawand.data.repository.auth.AuthRepository

object RepositoryProvider {
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(AuthServiceImpl())

    fun provideUserRepository(): UserRepository = UserRepositoryImpl(UserServiceImpl())

    fun provideStoryRepository(): StoryRepository = StoryRepositoryImpl(StoryServiceImpl())
}