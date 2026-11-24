package com.nexatv.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexatv.app.data.NexaRepository
import com.nexatv.app.data.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NexaRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ResultState<List<com.nexatv.app.data.ContentItem>>>(
        ResultState.Loading
    )

    val state: StateFlow<ResultState<List<com.nexatv.app.data.ContentItem>>> =
        _state.asStateFlow()

    fun loadHome() {
        viewModelScope.launch {
            _state.value = ResultState.Loading

            when (val result = repository.getTrending()) {
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
