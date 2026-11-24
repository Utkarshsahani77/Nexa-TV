package com.nexatv.app.data

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StalkerService {

    @GET("portal.php")
    suspend fun handshake(
        @Query("type") type: String = "stb",
        @Query("action") action: String = "handshake",
        @Header("Cookie") mac: String
    ): ResponseBody

    @GET("portal.php")
    suspend fun getCategories(
        @Query("type") type: String = "itv",
        @Query("action") action: String = "get_all_channels",
        @Header("Cookie") mac: String,
        @Query("JsHttpRequest") js: String = "1",
        @Query("token") token: String
    ): ResponseBody

    @GET("portal.php")
    suspend fun getChannels(
        @Query("type") type: String = "itv",
        @Query("action") action: String = "get_ordered_list",
        @Header("Cookie") mac: String,
        @Query("JsHttpRequest") js: String = "1",
        @Query("token") token: String,
        @Query("category") categoryId: String
    ): ResponseBody
}
