package com.littlewind.jetflix.data.api

import com.littlewind.jetflix.data.entities.genre.GenresResponse
import com.littlewind.jetflix.data.entities.movie.DiscoverMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieApi {
    @GET("discover/movie")
    @JvmSuppressWildcards
    suspend fun fetchMovies(
        @Query("page") pageNumber: Int,
        @QueryMap options: Map<String, Any>
    ): DiscoverMoviesResponse

    @GET("genre/movie/list")
    suspend fun fetchGenres(): GenresResponse
}