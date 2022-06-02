package com.mits.moviesapp.di

import com.mits.moviesapp.data.remote.TheMovieDbApi
import com.mits.moviesapp.data.repository.MediaRepositoryImpl
import com.mits.moviesapp.domain.repository.MediaRepository
import com.mits.moviesapp.domain.use_case.search_use_cases.SearchMediaItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMediaRepository(api: TheMovieDbApi): MediaRepository {
        return MediaRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSearchMediaItemUseCase(repository: MediaRepository): SearchMediaItemUseCase{
        return SearchMediaItemUseCase(repository)
    }
}