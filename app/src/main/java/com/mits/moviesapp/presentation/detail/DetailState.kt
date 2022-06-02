package com.mits.moviesapp.presentation.detail

import com.mits.moviesapp.domain.model.MediaDetail

data class DetailState(
    val isLoading: Boolean = false,
    val detailItem: MediaDetail? = null,
    val error: String = ""
)