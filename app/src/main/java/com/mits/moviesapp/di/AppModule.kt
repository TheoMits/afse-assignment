package com.mits.moviesapp.di

import com.mits.moviesapp.data.local.MediaItemDatabase
import com.mits.moviesapp.data.remote.TheMovieDbApi
import com.mits.moviesapp.data.repository.MediaRepositoryImpl
import com.mits.moviesapp.domain.repository.MediaRepository
import com.mits.moviesapp.domain.use_case.detail_use_cases.DetailUseCases
import com.mits.moviesapp.domain.use_case.detail_use_cases.GetMovieDetailsUseCase
import com.mits.moviesapp.domain.use_case.detail_use_cases.GetTvShowDetailsUseCase
import com.mits.moviesapp.domain.use_case.search_use_cases.SearchMediaItemUseCase
import com.mits.moviesapp.domain.use_case.search_use_cases.SearchUseCases
import com.mits.moviesapp.domain.use_case.watch_list_use_cases.*
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
    fun provideMediaRepository(api: TheMovieDbApi, dao: MediaItemDatabase): MediaRepository {
        return MediaRepositoryImpl(api, dao.mediaItemDao)
    }

    @Provides
    @Singleton
    fun provideSearchUseCases(repository: MediaRepository): SearchUseCases {
        return SearchUseCases(
            GetWatchListUseCase(repository),
            SearchMediaItemUseCase(repository),
            SearchMediaItemsInDBUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideDetailUseCases(repository: MediaRepository): DetailUseCases {
        return DetailUseCases(
            GetMovieDetailsUseCase(repository),
            GetTvShowDetailsUseCase(repository),
            DeleteMediaItemFromDBUseCase(repository),
            FindMediaItemInDBUseCase(repository),
            InsertMediaItemInDBUseCase(repository),
            GetMediaItemByIdFromDBUseCase(repository)
        )
    }


}