package com.littlewind.jetflix.domain.interactors

import androidx.paging.PagingData
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.Movie
import com.littlewind.jetflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : SubjectInteractor<FilterMovieParams, PagingData<Movie>>() {
    override fun createObservable(params: FilterMovieParams): Flow<PagingData<Movie>> {
        return movieRepository.fetchMovies(params)
    }
}