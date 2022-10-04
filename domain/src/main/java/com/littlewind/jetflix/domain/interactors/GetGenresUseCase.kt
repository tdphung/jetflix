package com.littlewind.jetflix.domain.interactors

import com.littlewind.jetflix.domain.model.genre.Genre
import com.littlewind.jetflix.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : ResultInteractor<Unit, List<Genre>>() {
    override suspend fun doWork(params: Unit): List<Genre> {
        return movieRepository.fetchGenres()
    }
}