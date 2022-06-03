package com.mits.moviesapp.domain.model

data class MediaDetail(
    val title: String,
    val posterPath: String?,
    val backDropPath: String?,
    val summary: String,
    val genres: List<String>
)
