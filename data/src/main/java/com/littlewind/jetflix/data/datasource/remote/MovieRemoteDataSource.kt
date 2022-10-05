package com.littlewind.jetflix.data.datasource.remote

import com.littlewind.jetflix.data.api.MovieApi
import com.littlewind.jetflix.data.entities.genre.GenresResponse
import com.littlewind.jetflix.data.entities.movie.DiscoverMoviesResponse
import com.littlewind.jetflix.data.entities.movie.MovieDetailResponse
import retrofit2.http.Path
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

    suspend fun fetchGenres(): GenresResponse {
        return api.fetchGenres()
    }

    suspend fun fetchMovieDetail(movieId: Int): MovieDetailResponse {
        return api.fetchMovieDetail(movieId)
    }
}