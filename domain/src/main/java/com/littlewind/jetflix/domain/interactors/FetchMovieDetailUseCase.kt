package com.littlewind.jetflix.domain.interactors

import com.littlewind.jetflix.domain.model.movie.MovieDetail
import com.littlewind.jetflix.domain.repository.MovieRepository
import javax.inject.Inject

class FetchMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : ResultInteractor<Int, MovieDetail>() {
    override suspend fun doWork(params: Int): MovieDetail {
        return movieRepository.fetchMovieDetail(params)
    }
}