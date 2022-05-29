package com.mits.moviesapp.domain.use_case.get_tv_show_details

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.model.MediaDetails
import com.mits.moviesapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    operator fun invoke(apiKey: String, tvShowId: Int): Flow<Resource<MediaDetails>> {
        return repository.getTvShowById(apiKey, tvShowId)
    }
}