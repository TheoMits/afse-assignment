package com.mits.moviesapp.domain.repository

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.model.MediaDetail
import com.mits.moviesapp.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun searchMediaItems(apiKey: String, query: String, page: Int): Flow<Resource<List<SearchItem>>>

    fun getMovieById(apiKey: String, movieId: Int): Flow<Resource<MediaDetail>>

    fun getTvShowById(apiKey: String, tvShowId: Int): Flow<Resource<MediaDetail>>

    fun getWatchList(): Flow<Resource<List<SearchItem>>>

    fun getMediaItemByIdFromDB(id: Int): Flow<Resource<MediaDetail>>

    fun searchMediaItemsInDB(query: String): Flow<Resource<List<SearchItem>>>

    fun insertMediaItemInDB(mediaDetail: MediaDetail): Flow<Resource<Unit>>

    fun deleteMediaItemFromDB(mediaDetail: MediaDetail): Flow<Resource<Unit>>

    fun checkIfMediaItemExistsInDB(id: Int): Flow<Resource<Boolean>>
}