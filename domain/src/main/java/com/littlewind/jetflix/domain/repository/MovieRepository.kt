package com.littlewind.jetflix.domain.repository

import androidx.paging.PagingData
import com.littlewind.jetflix.domain.model.genre.Genre
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchMovies(
        filterMovieParams: FilterMovieParams,
    ): Flow<PagingData<Movie>>

    suspend fun fetchGenres(): List<Genre>
}