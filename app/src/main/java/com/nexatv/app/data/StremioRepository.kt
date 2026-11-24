package com.nexatv.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class StremioResult<out T> {
    data class Success<T>(val data: T) : StremioResult<T>()
    data class Error(val message: String) : StremioResult<Nothing>()
}

class StremioRepository(
    private val apiKeys: ApiKeys,
    private val database: LocalDatabase
) {

    suspend fun getManifest(
        addonUrl: String
    ): StremioResult<StremioAddon> = withContext(Dispatchers.IO) {
        try {
            val service = NetworkModule.createStremioService(addonUrl)
            val response = service.getManifest()

            StremioResult.Success(response)
        } catch (e: Exception) {
            StremioResult.Error(e.message ?: "Failed to load addon")
        }
    }

    suspend fun getCatalog(
        addonUrl: String,
        catalogId: String,
        catalogType: String
    ): StremioResult<List<StremioMeta>> = withContext(Dispatchers.IO) {
        try {
            val service = NetworkModule.createStremioService(addonUrl)
            val response = service.getCatalog(
                type = catalogType,
                catalogId = catalogId
            )

            StremioResult.Success(response.metas)
        } catch (e: Exception) {
            StremioResult.Error(e.message ?: "Failed to load catalog")
        }
    }

    suspend fun getMeta(
        addonUrl: String,
        type: String,
        metaId: String
    ): StremioResult<StremioMeta> = withContext(Dispatchers.IO) {
        try {
            val service = NetworkModule.createStremioService(addonUrl)
            val response = service.getMeta(
                type = type,
                metaId = metaId
            )

            StremioResult.Success(response.meta)
        } catch (e: Exception) {
            StremioResult.Error(e.message ?: "Failed to load meta")
        }
    }

    suspend fun getStreams(
        addonUrl: String,
        type: String,
        metaId: String
    ): StremioResult<List<StremioStream>> = withContext(Dispatchers.IO) {
        try {
            val service = NetworkModule.createStremioService(addonUrl)
            val response = service.getStreams(
                type = type,
                metaId = metaId
            )

            StremioResult.Success(response.streams)
        } catch (e: Exception) {
            StremioResult.Error(e.message ?: "Failed to load streams")
        }
    }

    suspend fun getFavourites(): List<FavouriteEntity> = withContext(Dispatchers.IO) {
        database.favouriteDao().getAll().let { flow ->
            kotlinx.coroutines.flow.first(flow)
        }
    }

    suspend fun addFavourite(item: FavouriteEntity) = withContext(Dispatchers.IO) {
        database.favouriteDao().insert(item)
    }

    suspend fun removeFavourite(id: String) = withContext(Dispatchers.IO) {
        database.favouriteDao().delete(id)
    }

    suspend fun getWatchHistory(): List<WatchHistoryEntity> = withContext(Dispatchers.IO) {
        database.watchHistoryDao().getAll().let { flow ->
            kotlinx.coroutines.flow.first(flow)
        }
    }

    suspend fun addWatchHistory(item: WatchHistoryEntity) = withContext(Dispatchers.IO) {
        database.watchHistoryDao().insert(item)
    }
}
