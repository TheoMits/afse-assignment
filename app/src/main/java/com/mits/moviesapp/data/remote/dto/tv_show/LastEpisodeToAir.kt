package com.mits.moviesapp.data.remote.dto.tv_show

import com.google.gson.annotations.SerializedName

data class LastEpisodeToAir(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode_number")
    val episodeNumber: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("production_code")
    val productionCode: String,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("still_path")
    val stillPath: Any,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)