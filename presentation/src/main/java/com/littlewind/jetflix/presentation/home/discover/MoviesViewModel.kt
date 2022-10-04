package com.littlewind.jetflix.presentation.home.discover

import androidx.lifecycle.ViewModel
import com.littlewind.jetflix.domain.interactors.DiscoverMoviesUseCase
import com.littlewind.jetflix.domain.model.FilterMovieParams
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    discoverMoviesUseCase: DiscoverMoviesUseCase,
) : ViewModel() {
    init {
        discoverMoviesUseCase(FilterMovieParams())
    }

    val movies = discoverMoviesUseCase.flow
}