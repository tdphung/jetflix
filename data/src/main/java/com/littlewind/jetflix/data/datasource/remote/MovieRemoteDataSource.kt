package com.littlewind.jetflix.data.datasource.remote

import com.littlewind.jetflix.data.api.MovieApi
import com.littlewind.jetflix.data.entities.DiscoverMoviesResponse
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val api: MovieApi,
) {
    suspend fun fetchMovies(
        pageNumber: Int,
        options: Map<String, java.io.Serializable>
    ): DiscoverMoviesResponse {
        return api.fetchMovies(pageNumber, options)
    }
}