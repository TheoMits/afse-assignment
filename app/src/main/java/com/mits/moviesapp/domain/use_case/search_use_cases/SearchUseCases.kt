package com.mits.moviesapp.domain.use_case.search_use_cases

import com.mits.moviesapp.domain.use_case.watch_list_use_cases.GetWatchListUseCase
import com.mits.moviesapp.domain.use_case.watch_list_use_cases.SearchMediaItemsInDBUseCase

data class SearchUseCases(
    val getWatchListUseCase: GetWatchListUseCase,
    val searchMediaItemUseCase: SearchMediaItemUseCase,
    val searchMediaItemsInDBUseCase: SearchMediaItemsInDBUseCase
)
