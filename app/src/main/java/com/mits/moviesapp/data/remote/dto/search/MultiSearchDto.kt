package com.mits.moviesapp.data.remote.dto.search

import com.google.gson.annotations.SerializedName

data class MultiSearchDto(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
