package com.littlewind.jetflix.domain.interactors

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.Movie
import com.littlewind.jetflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : PagingInteractor<DiscoverMoviesUseCase.Params, Movie>() {
    override fun createObservable(params: Params): Flow<PagingData<Movie>> {
        Log.d("Phungtd", "new Pagerr")
        return Pager(
            config = params.pagingConfig,
            pagingSourceFactory = { movieRepository.fetchMovies(params.filterMovie) }
        ).flow
    }

    data class Params(
        override val pagingConfig: PagingConfig,
        val filterMovie: FilterMovieParams,
    ) : Parameters
}
