package com.nexatv.app.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nexatv.app.data.NexaRepository

class SeriesViewModelFactory(
    private val repository: NexaRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeriesViewModel::class.java)) {
            return SeriesViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
