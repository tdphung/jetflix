package com.littlewind.jetflix.presentation.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.littlewind.android.base.platform.BaseViewModel
import com.littlewind.jetflix.domain.interactors.FetchMovieDetailUseCase
import com.littlewind.jetflix.domain.model.movie.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetailUseCase: FetchMovieDetailUseCase,
) : BaseViewModel() {
    data class MovieDetailUiState(
        val movieDetail: MovieDetail? = null,
        val loading: Boolean = false,
        val error: Throwable? = null
    )

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        val movieId: Int = savedStateHandle.get<String>(MovieDetailScreen.ARG_MOVIE_ID)!!.toInt()
        fetchMovieDetail(movieId = movieId)
    }

    private fun fetchMovieDetail(movieId: Int) {
        _uiState.update {
            it.copy(loading = true, error = null)
        }
        try {
            viewModelScope.launch {
                movieDetailUseCase(movieId).lastOrNull()?.let { movieDetail ->
                    _uiState.update {
                        it.copy(
                            movieDetail = movieDetail,
                            loading = false,
                            error = null,
                        )
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    loading = false,
                    error = e,
                )
            }
        }
    }

}