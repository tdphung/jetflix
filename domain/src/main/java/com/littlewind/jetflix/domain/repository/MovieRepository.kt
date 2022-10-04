package com.littlewind.jetflix.domain.repository

import androidx.paging.PagingData
import com.littlewind.jetflix.domain.model.FilterMovieParams
import com.littlewind.jetflix.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchMovies(
        filterMovieParams: FilterMovieParams,
    ): Flow<PagingData<Movie>>
}