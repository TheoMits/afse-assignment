package com.mits.moviesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mits.moviesapp.common.enums.MediaType

@Entity(tableName = "mediaItemsDB")
data class MediaDetail(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val backDropPath: String?,
    val summary: String,
    var mediaType: MediaType?,
    val ratings: Double,
    val genre: String
)

fun MediaDetail.toSearchItem(): SearchItem {
    return SearchItem(
        id = id,
        title = title,
        imagePath = posterPath,
        mediaType = mediaType,
        ratings = ratings
    )
}
