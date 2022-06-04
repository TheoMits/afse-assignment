package com.mits.moviesapp.data.local

import androidx.room.*
import com.mits.moviesapp.domain.model.MediaDetail

@Dao
interface MediaItemDao {
    @Query("SELECT * FROM mediaItemsDB")
    suspend fun getMediaItems(): List<MediaDetail>

    @Query("SELECT * FROM mediaItemsDB WHERE id = :id")
    suspend fun getMediaItemById(id: Int): MediaDetail

    @Query("SELECT * FROM mediaItemsDB WHERE title LIKE '%' || :query || '%'")
    suspend fun searchMediaItems(query: String): List<MediaDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaItem(mediaDetail: MediaDetail)

    @Delete
    suspend fun deleteMediaItem(mediaDetail: MediaDetail)

    @Query("SELECT EXISTS (SELECT 1 FROM mediaItemsDB WHERE id = :id)")
    suspend fun exists(id: Int): Boolean
}