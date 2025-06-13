package co.wainow.sliideusers.di

import co.wainow.sliideusers.data.UserRepositoryImpl
import co.wainow.sliideusers.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsRepository(repository: UserRepositoryImpl): UserRepository
}