package com.nexatv.app.data

data class StremioAddon(
    val id: String,
    val name: String,
    val version: String,
    val description: String,
    val types: List<String>,
    val catalogs: List<StremioCatalog>,
    val resources: List<StremioResource>
)

data class StremioCatalog(
    val id: String,
    val name: String,
    val type: String
)

data class StremioResource(
    val name: String,
    val types: List<String>
)

data class StremioMeta(
    val id: String,
    val name: String,
    val type: String,
    val poster: String,
    val background: String,
    val description: String,
    val releaseInfo: String,
    val imdbRating: String,
    val videos: List<StremioVideo>
)

data class StremioVideo(
    val id: String,
    val title: String,
    val season: Int?,
    val episode: Int?,
    val released: String?
)

data class StremioStream(
    val title: String,
    val url: String,
    val quality: String,
    val size: String
)
