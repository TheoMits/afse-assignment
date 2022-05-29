package com.mits.moviesapp.data.repository

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.data.remote.TheMovieDbApi
import com.mits.moviesapp.data.remote.dto.movie.toMediaDetail
import com.mits.moviesapp.data.remote.dto.search.toMediaItem
import com.mits.moviesapp.domain.model.MediaDetails
import com.mits.moviesapp.domain.model.SearchItem
import com.mits.moviesapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val api: TheMovieDbApi
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
                    api.searchMediaItems(apiKey, query, page).results.map { it.toMediaItem() }
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong", e.code()))
            } catch (e: IOException) {
                emit(Resource.Error("Check your internet connection", 0))
            }
        }
        return response

    }

    override fun getMovieById(apiKey: String, movieId: Int): Flow<Resource<MediaDetails>> {
        val response = flow {
            try {
                emit(Resource.Loading())
                val response = api.getMovieById(apiKey, movieId).toMediaDetail()
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

    override fun getTvShowById(apiKey: String, tvShowId: Int): Flow<Resource<MediaDetails>> {
        val response = flow {
            try {
                emit(Resource.Loading())
                val response = api.getMovieById(apiKey, tvShowId).toMediaDetail()
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

}