package com.mits.moviesapp.presentation.search

import com.mits.moviesapp.domain.model.SearchItem

data class SearchState(
    val isLoading: Boolean = false,
    val searchList: MutableList<SearchItem> = mutableListOf(),
    val error: String = ""
)
