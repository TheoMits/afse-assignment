package com.mits.moviesapp.presentation.detail

import android.view.View
import com.mits.moviesapp.domain.model.MediaDetail

data class DetailState(
    val isLoading: Boolean = false,
    val detailItem: MediaDetail? = null,
    val isInWatchList: Boolean = false,
    val error: String = "",
    val view: View? = null
)