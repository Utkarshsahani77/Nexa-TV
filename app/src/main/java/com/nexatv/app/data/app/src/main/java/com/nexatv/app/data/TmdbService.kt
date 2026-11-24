package com.nexatv.app.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class TmdbResponse(
    val results: List<TmdbItem>
)

data class TmdbItem(
    val id: Int,
    val title: String?,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val first_air_date: String?,
    val vote_average: Double?,
    val genre_ids: List<Int>?,
    val media_type: String?,
    val imdb_id: String?
)

data class TmdbDetailsResponse(
    val id: Int,
    val title: String?,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val first_air_date: String?,
    val vote_average: Double?,
    val genres: List<TmdbGenre>?,
    val runtime: Int?,
    val episode_run_time: List<Int>?,
    val imdb_id: String?
)

data class TmdbGenre(
    val id: Int,
    val name: String
)

fun TmdbItem.toContentItem(): ContentItem {
    val title = title ?: name ?: ""
    val date = release_date ?: first_air_date ?: ""
    val poster = poster_path ?: ""
    val backdrop = backdrop_path ?: ""
    val type = media_type ?: if (title != null) "movie" else "tv"

    return ContentItem(
        id = id.toString(),
        title = title,
        overview = overview ?: "",
        poster = "https://image.tmdb.org/t/p/w500$poster",
        backdrop = "https://image.tmdb.org/t/p/w780$backdrop",
        type = type,
        year = date.take(4),
        rating = (vote_average ?: 0.0).toString(),
        genres = "",
        streamUrl = ""
    )
}

fun TmdbDetailsResponse.toContentItem(): ContentItem {
    val title = title ?: name ?: ""
    val date = release_date ?: first_air_date ?: ""
    val poster = poster_path ?: ""
    val backdrop = backdrop_path ?: ""
    val type = if (title != null) "movie" else "tv"

    return ContentItem(
        id = id.toString(),
        title = title,
        overview = overview ?: "",
        poster = "https://image.tmdb.org/t/p/w500$poster",
        backdrop = "https://image.tmdb.org/t/p/w780$backdrop",
        type = type,
        year = date.take(4),
        rating = (vote_average ?: 0.0).toString(),
        genres = genres?.joinToString(", ") { it.name } ?: "",
        streamUrl = ""
    )
}

interface TmdbService {

    @GET("trending/all/week")
    suspend fun getTrending(): TmdbResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(): TmdbResponse

    @GET("tv/popular")
    suspend fun getPopularSeries(): TmdbResponse

    @GET("search/multi")
    suspend fun search(
        @Query("query") query: String
    ): TmdbResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String
    ): TmdbDetailsResponse

    @GET("tv/{tv_id}")
    suspend fun getSeriesDetails(
        @Path("tv_id") tvId: String
    ): TmdbDetailsResponse
}
