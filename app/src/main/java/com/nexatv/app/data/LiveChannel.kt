package com.nexatv.app.data

data class LiveChannel(
    val id: String,
    val name: String,
    val logo: String,
    val url: String,
    val category: String
)

data class LiveCategory(
    val name: String,
    val channels: List<LiveChannel>
)
