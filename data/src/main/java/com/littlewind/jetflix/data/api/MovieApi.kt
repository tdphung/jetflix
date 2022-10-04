package com.littlewind.jetflix.data.api

import com.littlewind.jetflix.data.entities.DiscoverMoviesResponse
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
}