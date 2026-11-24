package com.nexatv.app.ui.livetv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexatv.app.data.LiveCategory
import com.nexatv.app.data.LiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LiveTvViewModel : ViewModel() {

    private val _categories =
        MutableStateFlow<List<LiveCategory>>(emptyList())

    val categories: StateFlow<List<LiveCategory>> =
        _categories.asStateFlow()

    private val _currentChannel =
        MutableStateFlow<LiveChannel?>(null)

    val currentChannel: StateFlow<LiveChannel?> =
        _currentChannel.asStateFlow()

    fun loadChannels() {
        viewModelScope.launch {
            val list = listOf(
                LiveCategory(
                    name = "News",
                    channels = listOf(
                        LiveChannel(
                            id = "1",
                            name = "News Channel 1",
                            logo = "",
                            url = "https://example.com/stream1.m3u8",
                            category = "News"
                        )
                    )
                )
            )

            _categories.value = list
        }
    }

    fun selectChannel(channel: LiveChannel) {
        _currentChannel.value = channel
    }
}
