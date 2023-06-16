package com.example.data.module

import com.example.data.repositoryimpl.SoundRepositoryImpl
import com.example.domain.repository.SoundRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        musicRepositoryImpl: SoundRepositoryImpl
    ): SoundRepository
}