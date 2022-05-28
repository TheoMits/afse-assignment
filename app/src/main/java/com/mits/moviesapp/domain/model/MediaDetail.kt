package com.mits.moviesapp.domain.model

data class MediaDetail(
    val title: String,
    val imagePath: String,
    val summary: String,
    val genres: List<String>
)
