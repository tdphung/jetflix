package com.littlewind.jetflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.littlewind.jetflix.data.datasource.remote.MovieRemoteDataSource
import com.littlewind.jetflix.data.entities.MovieResponseMapper
import com.littlewind.jetflix.domain.model.FilterMovieParams
import com.littlewind.jetflix.domain.model.Movie
import com.littlewind.jetflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieResponseMapper,
) : MovieRepository {
    override fun fetchMovies(
        filterMovieParams: FilterMovieParams
    ): Flow<PagingData<Movie>> {
        val pagingSource = MoviesPagingSource(
            movieRemoteDataSource = movieRemoteDataSource,
            movieMapper = movieMapper,
            filterParams = filterMovieParams,
        )
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource },
        ).flow
    }
}