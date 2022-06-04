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
    private var isWatchListEnabled = false
    private var query = ""
    private var menuIcon = R.drawable.tv_disabled

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

    private fun getWatchList() {
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

    fun isWatchListEnabled(): Boolean {
        return isWatchListEnabled
    }

    fun getQuery(): String {
        return query
    }

    fun getMenuIcon(): Int {
        return menuIcon
    }

    fun onRecyclerScroll() {
        if (!_searchState.value.isLoading && !isWatchListEnabled) {
            searchPage++
            searchMediaItems(API_KEY, query, searchPage)
        }
    }

    fun onSearchTextChanged(query: String) {
        // set new query
        this.query = query
        if (isWatchListEnabled) {
            when (query.isEmpty()) {
                true -> getWatchList()
                false -> searchMediaItemsInDB(query)
            }
        } else {
            // empty the list
            mediaItems = mutableListOf()
            // reset page
            searchPage = 1
            // make the call
            searchMediaItems(API_KEY, query, searchPage)
        }
    }

    fun disableWatchList() {
        resetValues()
        menuIcon = R.drawable.tv_disabled
        searchMediaItems(API_KEY, query, searchPage)
        isWatchListEnabled = false
    }

    fun enableWatchList() {
        resetValues()
        menuIcon = R.drawable.tv_enabled
        getWatchList()
        isWatchListEnabled = true
    }

    fun onUpdateView() {
        if (isWatchListEnabled) {
            if (query.isNotEmpty())
                searchMediaItemsInDB(query)
            else getWatchList()
        }
    }

    private fun resetValues() {
        mediaItems = mutableListOf()
        searchPage = 1
        query = ""
    }
}