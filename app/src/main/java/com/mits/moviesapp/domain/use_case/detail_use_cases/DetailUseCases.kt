package com.mits.moviesapp.domain.use_case.detail_use_cases

import com.mits.moviesapp.domain.use_case.watch_list_use_cases.DeleteMediaItemFromDBUseCase
import com.mits.moviesapp.domain.use_case.watch_list_use_cases.FindMediaItemInDBUseCase
import com.mits.moviesapp.domain.use_case.watch_list_use_cases.GetMediaItemByIdFromDBUseCase
import com.mits.moviesapp.domain.use_case.watch_list_use_cases.InsertMediaItemInDBUseCase

data class DetailUseCases(
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    val deleteMediaItemFromDBUseCase: DeleteMediaItemFromDBUseCase,
    val findMediaItemInDBUseCase: FindMediaItemInDBUseCase,
    val insertMediaItemInDBUseCase: InsertMediaItemInDBUseCase,
    val getMediaItemByIdFromDBUseCase: GetMediaItemByIdFromDBUseCase
)