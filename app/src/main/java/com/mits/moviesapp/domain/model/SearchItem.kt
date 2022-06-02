package com.mits.moviesapp.domain.model

import com.mits.moviesapp.common.enums.MediaType

data class SearchItem(
    val id: Int,
    val title: String,
    val imagePath: String?,
    val releaseDate: String?,
    val ratings: Double,
    val mediaType: MediaType
)
