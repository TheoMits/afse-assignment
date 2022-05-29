package com.mits.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mits.moviesapp.common.Resource
import com.mits.moviesapp.domain.use_case.get_movie_details.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getTvShowDetailsUseCase: GetMovieDetailsUseCase
): ViewModel() {

    private val _detailState: MutableLiveData<DetailState> = MutableLiveData()
    val detailState: LiveData<DetailState> = _detailState

    fun getMovieById(apiKey: String, movieId: Int) {
        getMovieDetailsUseCase(apiKey, movieId).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _detailState.value = DetailState(isLoading = true)
                }

                is Resource.Success -> {
                    _detailState.value = DetailState(detailItem = result.data)
                }

                is Resource.Error -> {
                    _detailState.value = DetailState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }

    fun getTvShowById(apiKey: String, id: Int) {
        getTvShowDetailsUseCase(apiKey, id).onEach { result ->
            when (result) {

                is Resource.Loading -> {
                    _detailState.value = DetailState(isLoading = true)
                }

                is Resource.Success -> {
                    _detailState.value = DetailState(detailItem = result.data)
                }

                is Resource.Error -> {
                    _detailState.value = DetailState(error = result.message ?: "An unexpected error occured")
                }

            }
        }.launchIn(viewModelScope)
    }
}