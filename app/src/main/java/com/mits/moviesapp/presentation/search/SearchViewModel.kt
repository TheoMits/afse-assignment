package com.mits.moviesapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mits.moviesapp.R
import com.mits.moviesapp.common.Constants.API_KEY
import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.model.SearchItem
import com.mits.moviesapp.domain.use_case.search_use_cases.SearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCases: SearchUseCases
) : ViewModel() {

    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    private var mediaItems = mutableListOf<SearchItem>()
    private var searchPage = 1
    var isWatchListEnabled = false
    var query = ""
    var menuIcon = R.drawable.tv_disabled

    init {
        searchMediaItems(API_KEY, query, searchPage)
    }

    private fun searchMediaItems(apiKey: String, query: String, page: Int) {
        searchUseCases.searchMediaItemUseCase(apiKey, query, page).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _searchState.value = SearchState(isLoading = true)
                }
                is Resource.Success -> {
                    result.data?.let { mediaItems.addAll(it) }
                    _searchState.value = SearchState(searchList = mediaItems)
                }
                is Resource.Error -> {
                    _searchState.value =
                        SearchState(error = result.message ?: if (mediaItems.isEmpty()) "No results." else "")
                }

            }
        }.launchIn(viewModelScope)
    }

    fun getWatchList() {
        searchUseCases.getWatchListUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _searchState.value = SearchState(isLoading = true)
                }
                is Resource.Success -> {
                    result.data?.let {
                        _searchState.value = SearchState(searchList = it.toMutableList(), error = if (it.isEmpty()) "No results." else "")
                    }
                }
                is Resource.Error -> {
                    _searchState.value =
                        SearchState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun searchMediaItemsInDB(query: String) {
        searchUseCases.searchMediaItemsInDBUseCase(query).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _searchState.value = SearchState(isLoading = true)
                }
                is Resource.Success -> {
                    result.data?.let {
                        _searchState.value = SearchState(searchList = it.toMutableList(), error = if (it.isEmpty()) "No results." else "")
                    }
                }
                is Resource.Error -> {
                    _searchState.value =
                        SearchState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }


    fun onRecyclerScroll() {
        if (!_searchState.value.isLoading && !isWatchListEnabled) {
            searchPage++
            searchMediaItems(API_KEY, query, searchPage)
        }
    }

    fun onSearchTextChanged(query: String) {
        if (!isWatchListEnabled) {
            // set new query
            this.query = query
            // empty the list
            mediaItems = mutableListOf()
            // reset page
            searchPage = 1
            // make the call
            searchMediaItems(API_KEY, query, searchPage)
        } else {
            when (query.isEmpty()) {
                true -> getWatchList()
                false -> searchMediaItemsInDB(query)
            }
        }
    }

    fun onItemClicked() {
        mediaItems = mutableListOf()
    }

    fun onWatchListEnabled() {
        menuIcon = R.drawable.tv_disabled
        mediaItems = mutableListOf()
        searchPage = 1
        query = ""
        searchMediaItems(API_KEY, query, searchPage)
        isWatchListEnabled = false
    }

    fun onWatchListDisabled() {
        menuIcon = R.drawable.tv_enabled
        mediaItems = mutableListOf()
        searchPage = 1
        query = ""
        getWatchList()
        isWatchListEnabled = true
    }
}