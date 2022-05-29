package com.mits.moviesapp.ui.detail

import com.mits.moviesapp.domain.model.MediaDetails

data class DetailState(
    val isLoading: Boolean = false,
    val detailItem: MediaDetails? = null,
    val error: String = ""
)