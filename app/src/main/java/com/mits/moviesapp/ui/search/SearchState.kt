package com.mits.moviesapp.ui.search

import com.mits.moviesapp.domain.model.SearchItem

data class SearchState(
    val isLoading: Boolean = false,
    val mediaList: List<SearchItem> = emptyList(),
    val error: String = ""
)
