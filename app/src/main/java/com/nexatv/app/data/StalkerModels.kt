package com.nexatv.app.data

data class StalkerAccount(
    val username: String,
    val password: String,
    val portalUrl: String,
    val mac: String,
    val expiry: String,
    val status: String
)

data class StalkerChannel(
    val id: String,
    val name: String,
    val logo: String,
    val url: String,
    val category: String
)

data class StalkerCategory(
    val name: String,
    val channels: List<StalkerChannel>
)

sealed class StalkerResult<out T> {
    data class Success<T>(val data: T) : StalkerResult<T>()
    data class Error(val message: String) : StalkerResult<Nothing>()
}
