package com.mits.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mits.moviesapp.domain.model.MediaDetail
import com.mits.moviesapp.domain.model.SearchItem

@Database(
    entities = [MediaDetail::class, SearchItem::class],
    version = 1
)
abstract class MediaItemDatabase: RoomDatabase() {
    abstract val mediaItemDao: MediaItemDao

    companion object {
        const val DATABASE_NAME = "mediaItems_db"
    }
}