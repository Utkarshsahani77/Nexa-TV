package com.nexatv.app.data

import retrofit2.http.GET
import retrofit2.http.Query

data class OmdbResponse(
    val Title: String?,
    val Year: String?,
    val Rated: String?,
    val Released: String?,
    val Runtime: String?,
    val Genre: String?,
    val Director: String?,
    val Writer: String?,
    val Actors: String?,
    val Plot: String?,
    val Language: String?,
    val Country: String?,
    val Awards: String?,
    val Poster: String?,
    val Metascore: String?,
    val imdbRating: String?,
    val imdbVotes: String?,
    val imdbID: String?,
    val Type: String?,
    val Response: String?
)

data class OmdbItem(
    val title: String,
    val year: String,
    val rated: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val actors: String,
    val plot: String,
    val poster: String,
    val imdbRating: String,
    val imdbId: String
)

fun OmdbResponse.toOmdbItem(): OmdbItem {
    return OmdbItem(
        title = Title ?: "",
        year = Year ?: "",
        rated = Rated ?: "",
        runtime = Runtime ?: "",
        genre = Genre ?: "",
        director = Director ?: "",
        actors = Actors ?: "",
        plot = Plot ?: "",
        poster = Poster ?: "",
        imdbRating = imdbRating ?: "",
        imdbId = imdbID ?: ""
    )
}

interface OmdbService {

    @GET(".")
    suspend fun getDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): OmdbResponse
}
