package com.littlewind.jetflix.presentation.home.discover

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.littlewind.android.base.platform.BaseViewModel
import com.littlewind.jetflix.domain.interactors.DiscoverMoviesUseCase
import com.littlewind.jetflix.domain.interactors.GetGenresUseCase
import com.littlewind.jetflix.domain.model.genre.Genre
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    discoverMoviesUseCase: DiscoverMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
) : BaseViewModel() {
    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20
        )
    }

    init {
    }

    val movies = discoverMoviesUseCase.flow.cachedIn(viewModelScope)

    private val _filterState: MutableStateFlow<FilterMovieParams?> = MutableStateFlow(null)
    val filterState: StateFlow<FilterMovieParams?> = _filterState.also {
        initFilterMovieParams()

        viewModelScope.launch {
            it.collect { params ->
                Log.d("Phungtd", "new filter State: $params")
                params?.let {
                    discoverMoviesUseCase(
                        DiscoverMoviesUseCase.Params(
                            pagingConfig = PAGING_CONFIG,
                            filterMovie = params
                        )
                    )
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