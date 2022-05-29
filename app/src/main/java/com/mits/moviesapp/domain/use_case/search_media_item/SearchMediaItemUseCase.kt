package com.mits.moviesapp.domain.use_case.search_media_item

import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.model.SearchItem
import com.mits.moviesapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMediaItemUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    operator fun invoke(apiKey: String, query: String, page: Int): Flow<Resource<List<SearchItem>>> {
        return repository.searchMediaItems(apiKey, query, page)
    }
}