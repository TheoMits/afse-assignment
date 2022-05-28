package com.mits.moviesapp.domain.model

data class Movie(
    val title: String,
    val imagePath: String,
    val summary: String,
    val genres: List<String>
)
