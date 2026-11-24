package com.nexatv.app.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val omdbRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createTmdbService(): TmdbService {
        return retrofit.create(TmdbService::class.java)
    }

    fun createOmdbService(): OmdbService {
        return omdbRetrofit.create(OmdbService::class.java)
    }

    fun createStremioService(baseUrl: String): StremioService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StremioService::class.java)
    }

    fun createStalkerService(baseUrl: String): StalkerService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StalkerService::class.java)
    }

    fun createStremioRepository(
        apiKeys: ApiKeys,
        database: LocalDatabase
    ): StremioRepository {
        return StremioRepository(
            apiKeys = apiKeys,
            database = database
        )
    }
}
