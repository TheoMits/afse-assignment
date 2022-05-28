package com.mits.moviesapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {
    @GET("/search/multi")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    )

    @GET("/{itemType}/{itemId}")
    suspend fun itemDetails(
        @Path("itemType") itemType: String,
        @Path("itemId") itemId: String,
        @Query("api_key") apiKey: String
    )
}