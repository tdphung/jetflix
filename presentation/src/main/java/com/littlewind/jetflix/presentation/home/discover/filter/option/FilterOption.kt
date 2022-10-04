package com.littlewind.jetflix.presentation.home.discover.filter.option

import androidx.compose.runtime.Composable
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams

interface FilterOption<Type : Any> {
    val defaultValue: Type
    var currentValue: Type

    fun modifyFilterState(filterState: FilterMovieParams): FilterMovieParams

    @Composable fun Render(onChanged: () -> Unit)
}
