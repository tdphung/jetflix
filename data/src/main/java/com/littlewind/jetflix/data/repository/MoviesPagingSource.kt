package com.littlewind.jetflix.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.littlewind.jetflix.data.datasource.remote.MovieRemoteDataSource
import com.littlewind.jetflix.data.entities.movie.MovieRequestOptionsMapper
import com.littlewind.jetflix.data.entities.movie.MovieResponseMapper
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.Movie

data class MoviesPagingSource(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieResponseMapper,
    private val filterParams: FilterMovieParams,
    private val movieRequestOptionsMapper: MovieRequestOptionsMapper,
) : PagingSource<Int, Movie>() {
    private val options = movieRequestOptionsMapper.map(filterParams)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val moviesResponse = movieRemoteDataSource.fetchMovies(page, options)
            val movies = moviesResponse.movies.map(movieMapper::mapToDomain)
            LoadResult.Page(
                data = movies,
                prevKey = if (page <= 1) null else page - 1,
                nextKey = if (page >= moviesResponse.totalPages) null else moviesResponse.page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = 1
}
