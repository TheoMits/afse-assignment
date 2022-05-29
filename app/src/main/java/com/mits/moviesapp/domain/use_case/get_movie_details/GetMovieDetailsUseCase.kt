package com.mits.moviesapp.domain.use_case.get_movie_details

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.model.MediaDetails
import com.mits.moviesapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    operator fun invoke(apiKey: String, movieId: String): Flow<Resource<MediaDetails>> {
        return repository.getMovieById(apiKey, movieId)
    }
}