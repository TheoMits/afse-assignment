package com.mits.moviesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mits.moviesapp.common.enums.MediaType

@Entity
data class SearchItem(
    @PrimaryKey val id: Int,
    val title: String,
    val imagePath: String?,
    val ratings: Double,
    val mediaType: MediaType?
)
