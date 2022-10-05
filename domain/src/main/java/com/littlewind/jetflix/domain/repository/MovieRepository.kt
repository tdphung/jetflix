package com.littlewind.jetflix.domain.repository

import androidx.paging.PagingSource
import com.littlewind.jetflix.domain.model.genre.Genre
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.Movie
import com.littlewind.jetflix.domain.model.movie.MovieDetail

interface MovieRepository {
    fun fetchMovies(
        filterMovieParams: FilterMovieParams,
    ): PagingSource<Int, Movie>

    suspend fun fetchGenres(): List<Genre>

    suspend fun fetchMovieDetail(movieId: Int): MovieDetail
}