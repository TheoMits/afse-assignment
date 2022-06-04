package com.mits.moviesapp.data.repository

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.common.enums.MediaType
import com.mits.moviesapp.data.local.MediaItemDao
import com.mits.moviesapp.data.remote.TheMovieDbApi
import com.mits.moviesapp.data.remote.dto.movie.toMediaDetail
import com.mits.moviesapp.data.remote.dto.search.toSearchItem
import com.mits.moviesapp.data.remote.dto.tv_show.toMediaDetail
import com.mits.moviesapp.domain.model.MediaDetail
import com.mits.moviesapp.domain.model.SearchItem
import com.mits.moviesapp.domain.model.toSearchItem
import com.mits.moviesapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val api: TheMovieDbApi,
    private val dao: MediaItemDao
) : MediaRepository {

    override fun searchMediaItems(
        apiKey: String,
        query: String,
        page: Int
    ): Flow<Resource<List<SearchItem>>> {
        val response = flow {
            try {
                emit(Resource.Loading())
                val response =
                    api.searchMediaItems(
                        apiKey,
                        query,
                        page
                    ).results.mapNotNull { if (it.mediaType != MediaType.PERSON) it.toSearchItem() else null }
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Error("No results.", e.code()))
            } catch (e: IOException) {
                emit(Resource.Error("Check your internet connection", 0))
            }
        }
        return response
    }

    override fun getMovieById(apiKey: String, movieId: Int): Flow<Resource<MediaDetail>> {
        val response = flow {
            try {
                emit(Resource.Loading())
                val response = api.getMovieById(movieId, apiKey).toMediaDetail()
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", e.code()))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Check your internet connection", 0
                    )
                )
            }
        }
        return response
    }

    override fun getTvShowById(apiKey: String, tvShowId: Int): Flow<Resource<MediaDetail>> {
        val response = flow {
            try {
                emit(Resource.Loading())
                val response = api.getTvShowById(tvShowId, apiKey).toMediaDetail()
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", e.code()))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Check your internet connection", 0
                    )
                )
            }
        }
        return response
    }

    override fun getWatchList(): Flow<Resource<List<SearchItem>>> {
        val mediaItems = flow {
            try {
                emit(Resource.Loading())
                val mediaItems = dao.getMediaItems().map { it.toSearchItem() }
                emit(Resource.Success(mediaItems))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", -1))
            }
        }
        return mediaItems
    }

    override fun searchMediaItemsInDB(query: String): Flow<Resource<List<SearchItem>>> {
        val mediaItems = flow {
            try {
                emit(Resource.Loading())
                val mediaItems = dao.searchMediaItems(query).map { it.toSearchItem() }
                emit(Resource.Success(mediaItems))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", -1))
            }
        }
        return mediaItems
    }

    override fun getMediaItemByIdFromDB(id: Int): Flow<Resource<MediaDetail>> {
        val mediaItem = flow {
            try {
                emit(Resource.Loading())
                val mediaItem = dao.getMediaItemById(id)
                emit(Resource.Success(mediaItem))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", -1))
            }
        }
        return mediaItem
    }

    override fun insertMediaItemInDB(mediaDetail: MediaDetail): Flow<Resource<Unit>> {
        val insertedMediaItem = flow {
            try {
                emit(Resource.Loading())
                val insertedMediaItem = dao.insertMediaItem(mediaDetail)
                emit(Resource.Success(insertedMediaItem))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", -1))
            }
        }
        return insertedMediaItem
    }

    override fun deleteMediaItemFromDB(mediaDetail: MediaDetail): Flow<Resource<Unit>> {
        val deletedMediaItem = flow {
            try {
                emit(Resource.Loading())
                val deletedMediaItem = dao.deleteMediaItem(mediaDetail)
                emit(Resource.Success(deletedMediaItem))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", -1))
            }
        }
        return deletedMediaItem
    }

    override fun checkIfMediaItemExistsInDB(id: Int): Flow<Resource<Boolean>> {
        val mediaItemExists = flow {
            try {
                emit(Resource.Loading())
                val mediaItemExists = dao.exists(id)
                emit(Resource.Success(mediaItemExists))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", -1))
            }
        }
        return mediaItemExists
    }

}