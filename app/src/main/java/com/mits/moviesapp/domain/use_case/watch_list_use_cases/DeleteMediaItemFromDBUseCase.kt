package com.mits.moviesapp.domain.use_case.watch_list_use_cases

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.model.MediaDetail
import com.mits.moviesapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMediaItemFromDBUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    operator fun invoke(mediaDetail: MediaDetail): Flow<Resource<Unit>> {
        return repository.deleteMediaItemFromDB(mediaDetail)
    }
}