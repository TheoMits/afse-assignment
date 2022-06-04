package com.mits.moviesapp.presentation.detail

import android.view.View
import androidx.lifecycle.*
import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.common.enums.MediaType
import com.mits.moviesapp.domain.model.MediaDetail
import com.mits.moviesapp.domain.use_case.detail_use_cases.DetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases
) : ViewModel() {

    private val _detailState: MutableStateFlow<DetailState> = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    fun getMediaItemById(apiKey: String, id: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> {
                detailUseCases.getMovieDetailsUseCase(apiKey, id).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _detailState.value = DetailState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _detailState.value = DetailState(detailItem = result.data, isInWatchList = false)
                        }

                        is Resource.Error -> {
                            _detailState.value =
                                DetailState(error = result.message ?: "An unexpected error occured")
                        }

                    }
                }.launchIn(viewModelScope)
            }
            MediaType.TV -> {
                detailUseCases.getTvShowDetailsUseCase(apiKey, id).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _detailState.value = DetailState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _detailState.value = DetailState(detailItem = result.data, isInWatchList = false)
                        }

                        is Resource.Error -> {
                            _detailState.value =
                                DetailState(error = result.message ?: "An unexpected error occured")
                        }

                    }
                }.launchIn(viewModelScope)
            }
        }

    }

    fun getMediaItemFromDB(id: Int) {
        detailUseCases.getMediaItemByIdFromDBUseCase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _detailState.value = DetailState(isLoading = false)
                }

                is Resource.Success -> {
                    result.data?.let { _detailState.value = DetailState(detailItem = it, isInWatchList = true) }
                }

                is Resource.Error -> {
                    _detailState.value =
                        DetailState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }

    fun insertMediaItemInDB(mediaItem: MediaDetail, view: View) {
        detailUseCases.insertMediaItemInDBUseCase(mediaItem).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _detailState.value = DetailState(isLoading = false)
                }

                is Resource.Success -> {
                    result.data?.let { _detailState.value = DetailState(isInWatchList = true, view = view) }
                }

                is Resource.Error -> {
                    _detailState.value =
                        DetailState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }

    fun deleteMediaItemFromDB(mediaItem: MediaDetail, view: View) {
        detailUseCases.deleteMediaItemFromDBUseCase(mediaItem).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _detailState.value = DetailState(isLoading = false)
                }

                is Resource.Success -> {
                    result.data?.let { _detailState.value = DetailState(isInWatchList = false, view = view) }
                }

                is Resource.Error -> {
                    _detailState.value =
                        DetailState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }

    fun findIfMediaItemExistsInDB(apiKey: String, mediaId: Int, mediaType: MediaType) {
        detailUseCases.findMediaItemInDBUseCase(mediaId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _detailState.value = DetailState(isLoading = false)
                }

                is Resource.Success -> {
                    result.data?.let {
                        if (result.data) getMediaItemFromDB(mediaId)
                        else getMediaItemById(apiKey, mediaId, mediaType)
                    }
                }

                is Resource.Error -> {
                    _detailState.value =
                        DetailState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }


}