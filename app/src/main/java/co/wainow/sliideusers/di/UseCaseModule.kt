package co.wainow.sliideusers.di

import co.wainow.sliideusers.domain.UserRepository
import co.wainow.sliideusers.domain.UserUseCase
import co.wainow.sliideusers.domain.UserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesUseCase(repo: UserRepository): UserUseCase {
        return UserUseCaseImpl(repo)
    }
}