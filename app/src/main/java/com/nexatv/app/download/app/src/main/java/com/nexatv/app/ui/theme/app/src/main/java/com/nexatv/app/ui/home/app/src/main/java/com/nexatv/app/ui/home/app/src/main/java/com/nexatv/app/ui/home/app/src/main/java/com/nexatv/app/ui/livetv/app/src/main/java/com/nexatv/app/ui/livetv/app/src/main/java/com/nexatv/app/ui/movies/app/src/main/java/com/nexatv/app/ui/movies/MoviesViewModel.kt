package com.nexatv.app.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexatv.app.data.ContentItem
import com.nexatv.app.data.NexaRepository
import com.nexatv.app.data.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: NexaRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow<ResultState<List<ContentItem>>>(ResultState.Loading)

    val state: StateFlow<ResultState<List<ContentItem>>> =
        _state.asStateFlow()

    fun loadMovies() {
        viewModelScope.launch {
            _state.value = ResultState.Loading

            when (val result = repository.getPopularMovies()) {
                is ResultState.Success -> {
                    _state.value = ResultState.Success(result.data)
                }

                is ResultState.Error -> {
                    _state.value = ResultState.Error(result.message)
                }

                else -> {}
            }
        }
    }
}
