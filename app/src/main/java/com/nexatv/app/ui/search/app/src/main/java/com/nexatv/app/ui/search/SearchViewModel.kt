package com.nexatv.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexatv.app.data.ContentItem
import com.nexatv.app.data.NexaRepository
import com.nexatv.app.data.ResultState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val repository: NexaRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")

    val query: StateFlow<String> =
        _query.asStateFlow()

    private val _state =
        MutableStateFlow<ResultState<List<ContentItem>>>(ResultState.Loading)

    val state: StateFlow<ResultState<List<ContentItem>>> =
        _state.asStateFlow()

    fun setQuery(value: String) {
        _query.value = value

        viewModelScope.launch {
            _query
                .debounce(500)
                .collect { q ->
                    if (q.isNotEmpty()) {
                        search(q)
                    }
                }
        }
    }

    private fun search(q: String) {
        viewModelScope.launch {
            _state.value = ResultState.Loading

            when (val result = repository.search(q)) {
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
