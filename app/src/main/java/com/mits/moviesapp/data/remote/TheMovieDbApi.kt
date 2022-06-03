package com.mits.moviesapp.data.remote

import com.mits.moviesapp.data.remote.dto.movie.MovieDto
import com.mits.moviesapp.data.remote.dto.search.MultiSearchDto
import com.mits.moviesapp.data.remote.dto.tv_show.TvShowDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {
    @GET("search/multi")
    suspend fun searchMediaItems(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MultiSearchDto

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDto

    @GET("tv/{tvShowId}")
    suspend fun getTvShowById(
        @Path("tvShowId") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): TvShowDto
}