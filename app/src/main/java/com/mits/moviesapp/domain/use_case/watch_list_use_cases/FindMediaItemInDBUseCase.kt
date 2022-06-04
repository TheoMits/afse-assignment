package com.mits.moviesapp.domain.use_case.watch_list_use_cases

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMediaItemInDBUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Boolean>> {
        return repository.checkIfMediaItemExistsInDB(id)
    }
}