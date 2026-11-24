package com.nexatv.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val message: String) : ResultState<Nothing>()
}

data class ContentItem(
    val id: String,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val type: String,
    val year: String,
    val rating: String,
    val genres: String,
    val streamUrl: String
)

class NexaRepository(
    private val tmdbService: TmdbService,
    private val omdbService: OmdbService,
    private val apiKeys: ApiKeys
) {

    suspend fun getTrending(): ResultState<List<ContentItem>> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.getTrending()
            val items = response.results.map { it.toContentItem() }
            ResultState.Success(items)
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getPopularMovies(): ResultState<List<ContentItem>> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.getPopularMovies()
            val items = response.results.map { it.toContentItem() }
            ResultState.Success(items)
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getPopularSeries(): ResultState<List<ContentItem>> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.getPopularSeries()
            val items = response.results.map { it.toContentItem() }
            ResultState.Success(items)
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun search(query: String): ResultState<List<ContentItem>> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.search(query)
            val items = response.results.map { it.toContentItem() }
            ResultState.Success(items)
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getDetails(id: String, type: String): ResultState<ContentItem> = withContext(Dispatchers.IO) {
        try {
            val response = if (type == "movie") {
                tmdbService.getMovieDetails(id)
            } else {
                tmdbService.getSeriesDetails(id)
            }

            val item = response.toContentItem()
            ResultState.Success(item)
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getOmdbDetailsByTmdbId(
        tmdbId: String,
        type: String
    ): ResultState<OmdbItem> = withContext(Dispatchers.IO) {
        try {
            val tmdbResponse = tmdbService.getMovieDetails(tmdbId)
            val imdbId = tmdbResponse.imdbId ?: ""

            val omdbResponse = omdbService.getDetails(
                apiKey = "",
                imdbId = imdbId
            )

            ResultState.Success(omdbResponse.toOmdbItem())
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Unknown error")
        }
    }
}
