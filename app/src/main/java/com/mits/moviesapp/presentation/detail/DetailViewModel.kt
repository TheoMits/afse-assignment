package com.mits.moviesapp.presentation.detail

import androidx.lifecycle.*
import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.common.enums.MediaType
import com.mits.moviesapp.domain.use_case.detail_use_cases.GetMovieDetailsUseCase
import com.mits.moviesapp.domain.use_case.detail_use_cases.GetTvShowDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
) : ViewModel() {

    private val _detailState: MutableStateFlow<DetailState> = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    fun getMediaItemById(apiKey: String, id: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> {
                getMovieDetailsUseCase(apiKey, id).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _detailState.value = DetailState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _detailState.value = DetailState(detailItem = result.data)
                        }

                        is Resource.Error -> {
                            _detailState.value =
                                DetailState(error = result.message ?: "An unexpected error occured")
                        }

                    }
                }.launchIn(viewModelScope)
            }
            MediaType.TV -> {
                getTvShowDetailsUseCase(apiKey, id).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _detailState.value = DetailState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _detailState.value = DetailState(detailItem = result.data)
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
}