package com.littlewind.jetflix.presentation.home.discover.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.littlewind.jetflix.common.ui.utils.toDp
import com.littlewind.jetflix.common.ui.widget.error.ErrorColumn
import com.littlewind.jetflix.common.ui.widget.error.ErrorRow
import com.littlewind.jetflix.common.ui.widget.loading.LoadingColumn
import com.littlewind.jetflix.common.ui.widget.loading.LoadingRow
import com.littlewind.jetflix.domain.model.movie.Movie
import com.littlewind.jetflix.presentation.R
import com.littlewind.jetflix.presentation.home.discover.LocalOnMovieItemClicked
import kotlinx.coroutines.flow.Flow

private const val COLUMN_COUNT = 2
private val GRID_SPACING = 8.dp

private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(COLUMN_COUNT) }

@Composable
fun MoviesGrid(state: LazyGridState, moviesFlow: Flow<PagingData<Movie>>) {
    val movies = moviesFlow.collectAsLazyPagingItems()

    when (movies.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingColumn(stringResource(id = R.string.fetching_movies))
        }
        is LoadState.Error -> {
            val error = movies.loadState.refresh as LoadState.Error
            ErrorColumn(error.error.message.orEmpty())
        }
        else -> {
            LazyMoviesGrid(state, movies)
        }
    }
}

@Composable
private fun LazyMoviesGrid(state: LazyGridState, moviePagingItems: LazyPagingItems<Movie>) {
    val onMovieClicked = LocalOnMovieItemClicked.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(COLUMN_COUNT),
        contentPadding = PaddingValues(
            start = GRID_SPACING,
            end = GRID_SPACING,
            bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current).toDp().dp.plus(
                GRID_SPACING
            )
        ),
        horizontalArrangement = Arrangement.spacedBy(GRID_SPACING, Alignment.CenterHorizontally),
        state = state,
        content = {
            if (moviePagingItems.itemCount == 0 && moviePagingItems.loadState.refresh !is LoadState.Loading) {
                item(span = span) {
                    ErrorRow(stringResource(R.string.no_movies_found))
                }
            }
            items(moviePagingItems.itemCount) { index ->
                val movie = moviePagingItems[index] ?: return@items
                ItemMovie(
                    movie = movie,
                    modifier = Modifier
                        .height(320.dp)
                        .padding(vertical = GRID_SPACING),
                    onMovieClicked = onMovieClicked ?: {},
                )
            }
            renderLoading(moviePagingItems.loadState)
            renderError(moviePagingItems.loadState)
        }
    )
}

private fun LazyGridScope.renderLoading(loadState: CombinedLoadStates) {
    if (loadState.append !is LoadState.Loading) return

    item(span = span) {
        val title = stringResource(R.string.fetching_more_movies)
        LoadingRow(title = title, modifier = Modifier.padding(vertical = GRID_SPACING))
    }
}

private fun LazyGridScope.renderError(loadState: CombinedLoadStates) {
    val message = (loadState.append as? LoadState.Error)?.error?.message ?: return

    item(span = span) {
        ErrorRow(title = message, modifier = Modifier.padding(vertical = GRID_SPACING))
    }
}
