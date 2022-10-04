package com.littlewind.jetflix.presentation.home.discover

import androidx.lifecycle.viewModelScope
import com.littlewind.android.base.platform.BaseViewModel
import com.littlewind.jetflix.domain.interactors.DiscoverMoviesUseCase
import com.littlewind.jetflix.domain.interactors.GetGenresUseCase
import com.littlewind.jetflix.domain.model.genre.Genre
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    discoverMoviesUseCase: DiscoverMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
) : BaseViewModel() {
    init {
    }

    val movies = discoverMoviesUseCase.flow

    private val _filterState: MutableStateFlow<FilterMovieParams?> = MutableStateFlow(null)
    val filterState: StateFlow<FilterMovieParams?> = _filterState.also {
        initFilterMovieParams()

        viewModelScope.launch {
            it.collect { params ->
                params?.let {
                    discoverMoviesUseCase(params)
                }
            }
        }
    }.asStateFlow()

    private val _genres: MutableStateFlow<List<Genre>?> = MutableStateFlow(null)
    val genres: StateFlow<List<Genre>?> = _genres.asStateFlow()

    private fun initFilterMovieParams() {
        _filterState.value = FilterMovieParams()
    }

    fun resetFilterMovieParams() {
        _filterState.value = FilterMovieParams()
    }

    fun setFilterParams(newFilter: FilterMovieParams) {
        _filterState.value = newFilter
    }

    fun fetchGenresIfNeeded() {
        if (_genres.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                _genres.value = getGenresUseCase(Unit).lastOrNull()
            }
        }
    }
}