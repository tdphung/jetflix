package com.littlewind.jetflix.data.repository

import androidx.paging.PagingSource
import com.littlewind.jetflix.data.datasource.remote.MovieRemoteDataSource
import com.littlewind.jetflix.data.entities.genre.GenreResponseMapper
import com.littlewind.jetflix.data.entities.movie.MovieDetailResponseMapper
import com.littlewind.jetflix.data.entities.movie.MovieRequestOptionsMapper
import com.littlewind.jetflix.data.entities.movie.MovieResponseMapper
import com.littlewind.jetflix.domain.model.genre.Genre
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.Movie
import com.littlewind.jetflix.domain.model.movie.MovieDetail
import com.littlewind.jetflix.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieResponseMapper,
    private val movieRequestOptionsMapper: MovieRequestOptionsMapper,
    private val genreMapper: GenreResponseMapper,
    private val movieDetailMapper: MovieDetailResponseMapper,
) : MovieRepository {
    override fun fetchMovies(
        filterMovieParams: FilterMovieParams
    ): PagingSource<Int, Movie> {
        return MoviesPagingSource(
            movieRemoteDataSource = movieRemoteDataSource,
            movieMapper = movieMapper,
            filterParams = filterMovieParams,
            movieRequestOptionsMapper = movieRequestOptionsMapper
        )
    }

    override suspend fun fetchGenres(): List<Genre> {
        return movieRemoteDataSource.fetchGenres().genres.map(genreMapper::mapToDomain)
    }

    override suspend fun fetchMovieDetail(movieId: Int): MovieDetail {
        return movieRemoteDataSource.fetchMovieDetail(movieId).run(movieDetailMapper::mapToDomain)
    }
}
