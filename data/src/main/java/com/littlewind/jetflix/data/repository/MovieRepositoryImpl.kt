package com.littlewind.jetflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.littlewind.jetflix.data.datasource.remote.MovieRemoteDataSource
import com.littlewind.jetflix.data.entities.genre.GenreResponseMapper
import com.littlewind.jetflix.data.entities.movie.MovieRequestOptionsMapper
import com.littlewind.jetflix.data.entities.movie.MovieResponseMapper
import com.littlewind.jetflix.domain.model.genre.Genre
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.Movie
import com.littlewind.jetflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieResponseMapper,
    private val movieRequestOptionsMapper: MovieRequestOptionsMapper,
    private val genreMapper: GenreResponseMapper,
) : MovieRepository {
    override fun fetchMovies(
        filterMovieParams: FilterMovieParams
    ): Flow<PagingData<Movie>> {
        val pagingSource = MoviesPagingSource(
            movieRemoteDataSource = movieRemoteDataSource,
            movieMapper = movieMapper,
            filterParams = filterMovieParams,
            movieRequestOptionsMapper = movieRequestOptionsMapper
        )
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource },
        ).flow
    }

    override suspend fun fetchGenres(): List<Genre> {
        return movieRemoteDataSource.fetchGenres().genres.map(genreMapper::mapToDomain)
    }
}