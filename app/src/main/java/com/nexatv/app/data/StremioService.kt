package com.nexatv.app.data

import retrofit2.http.GET
import retrofit2.http.Path

data class StremioCatalogResponse(
    val metas: List<StremioMeta>
)

data class StremioMetaResponse(
    val meta: StremioMeta
)

data class StremioStreamResponse(
    val streams: List<StremioStream>
)

interface StremioService {

    @GET("manifest.json")
    suspend fun getManifest(): StremioAddon

    @GET("catalog/{type}/{id}.json")
    suspend fun getCatalog(
        @Path("type") type: String,
        @Path("id") catalogId: String
    ): StremioCatalogResponse

    @GET("meta/{type}/{id}.json")
    suspend fun getMeta(
        @Path("type") type: String,
        @Path("id") metaId: String
    ): StremioMetaResponse

    @GET("stream/{type}/{id}.json")
    suspend fun getStreams(
        @Path("type") type: String,
        @Path("id") metaId: String
    ): StremioStreamResponse
}
