package com.mits.moviesapp.domain.repository

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.model.MediaDetails
import com.mits.moviesapp.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun searchMediaItems(apiKey: String, query: String, page: Int): Flow<Resource<List<SearchItem>>>

    fun getMovieById(apiKey: String, movieId: Int): Flow<Resource<MediaDetails>>

    fun getTvShowById(apiKey: String, tvShowId: Int): Flow<Resource<MediaDetails>>
}