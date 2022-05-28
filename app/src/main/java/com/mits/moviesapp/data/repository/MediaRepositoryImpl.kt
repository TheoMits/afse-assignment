package com.mits.moviesapp.data.repository

import com.mits.moviesapp.data.remote.TheMovieDbApi
import com.mits.moviesapp.data.remote.dto.movie.MovieDto
import com.mits.moviesapp.data.remote.dto.search.MultiSearchDto
import com.mits.moviesapp.data.remote.dto.tv_show.TvShowDto
import com.mits.moviesapp.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val api:  TheMovieDbApi
): MediaRepository {

    override suspend fun searchMediaItems(
        apiKey: String,
        query: String,
        page: Int
    ): MultiSearchDto {
        return api.searchMediaItems(apiKey, query, page)
    }

    override suspend fun getMovieById(apiKey: String, movieId: String): MovieDto {
        return api.getMovieById(apiKey, movieId)
    }

    override suspend fun getTvShowById(apiKey: String, tvShowId: String): TvShowDto {
        return api.getTvShowById(apiKey, tvShowId)
    }

}