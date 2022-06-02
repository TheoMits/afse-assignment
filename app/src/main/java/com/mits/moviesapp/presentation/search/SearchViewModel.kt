package com.mits.moviesapp.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.model.SearchItem
import com.mits.moviesapp.domain.use_case.search_use_cases.SearchMediaItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMediaItemUseCase: SearchMediaItemUseCase
) : ViewModel() {

    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()
    var mediaItems = mutableListOf<SearchItem>()
    var searchPage = 1
    var editable = ""

    fun searchMediaItems(apiKey: String, query: String, page: Int) {
        Log.e("query", query)
        searchMediaItemUseCase(apiKey, query, page).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _searchState.value = SearchState(isLoading = true)
                }
                is Resource.Success -> {
                    result.data?.let { mediaItems.addAll(it) }
                    _searchState.value = SearchState(mediaList = mediaItems)
                }
                is Resource.Error -> {
                    _searchState.value =
                        SearchState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }


}