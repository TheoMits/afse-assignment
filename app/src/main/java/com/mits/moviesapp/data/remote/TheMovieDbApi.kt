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
        @Query("api_key") apiKey: String,
        @Path("movieId") movieId: Int,
    ): MovieDto

    @GET("tv/{tvShowId}")
    suspend fun getTvShowById(
        @Query("api_key") apiKey: String,
        @Path("tvShowId") tvShowId: Int
    ): TvShowDto
}