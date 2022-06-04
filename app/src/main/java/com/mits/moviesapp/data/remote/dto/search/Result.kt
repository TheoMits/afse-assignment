package com.mits.moviesapp.data.remote.dto.search

import com.google.gson.annotations.SerializedName
import com.mits.moviesapp.common.enums.MediaType
import com.mits.moviesapp.domain.model.SearchItem

data class Result(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    val gender: Int,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerializedName("known_for")
    val knownFor: List<KnownFor>,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("media_type")
    val mediaType: MediaType,
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

fun Result.toSearchItem(): SearchItem {
    return SearchItem(
        id = id,
        title = if (name.isNullOrEmpty()) title!! else name,
        imagePath = posterPath,
        ratings = voteAverage,
        mediaType = mediaType
    )
}
