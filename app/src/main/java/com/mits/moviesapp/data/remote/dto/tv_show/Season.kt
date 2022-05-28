package com.mits.moviesapp.data.remote.dto.tv_show

import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode_count")
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int
)