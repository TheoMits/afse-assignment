package com.mits.moviesapp.di

import com.mits.moviesapp.data.remote.TheMovieDbApi
import com.mits.moviesapp.data.repository.MediaRepositoryImpl
import com.mits.moviesapp.domain.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMediaRepository(api: TheMovieDbApi): MediaRepository {
        return MediaRepositoryImpl(api)
    }
}