package com.mits.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.mits.moviesapp.data.local.MediaItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideMediaItemDatabase(app: Application): MediaItemDatabase {
        return Room.databaseBuilder(
            app,
            MediaItemDatabase::class.java,
            MediaItemDatabase.DATABASE_NAME
        ).build()
    }
}