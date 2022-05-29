package com.mits.moviesapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.use_case.search_media_item.SearchMediaItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMediaItemUseCase: SearchMediaItemUseCase
) : ViewModel() {

    private val _searchState: MutableLiveData<SearchState> = MutableLiveData()
    val searchState: LiveData<SearchState> = _searchState

    fun searchMediaItems(apiKey: String, query: String, page: Int) {
        searchMediaItemUseCase(apiKey, query, page).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _searchState.value = SearchState(isLoading = true)
                }

                is Resource.Success -> {
                    _searchState.value = SearchState(mediaList = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _searchState.value = SearchState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }


}