package com.mits.moviesapp.domain.repository

import com.mits.moviesapp.data.remote.dto.movie.MovieDto
import com.mits.moviesapp.data.remote.dto.search.MultiSearchDto
import com.mits.moviesapp.data.remote.dto.tv_show.TvShowDto

interface MediaRepository {
    suspend fun searchMediaItems(apiKey: String, query: String, page: Int): MultiSearchDto

    suspend fun getMovieById(apiKey: String, movieId: String): MovieDto

    suspend fun getTvShowById(apiKey: String, tvShowId: String): TvShowDto
}