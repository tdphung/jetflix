package com.littlewind.jetflix.presentation.home.discover.filter.option

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.SortBy
import com.littlewind.jetflix.presentation.R
import com.littlewind.jetflix.presentation.home.discover.filter.FilterGrid
import com.littlewind.jetflix.presentation.home.discover.filter.FilterRadioItem
import com.littlewind.jetflix.presentation.home.discover.filter.FilterSectionDivider
import com.littlewind.jetflix.presentation.home.discover.filter.FilterSectionTitle

data class SortByOption(override val defaultValue: SortBy) : FilterOption<SortBy> {
    override var currentValue: SortBy = defaultValue

    override fun modifyFilterState(filterState: FilterMovieParams) =
        filterState.copy(sortBy = currentValue)

    @Composable
    override fun Render(onChanged: () -> Unit) {
        val sortByState = remember(defaultValue) { mutableStateOf(currentValue) }
        FilterSectionTitle(
            painter = rememberVectorPainter(image = Icons.Default.Sort),
            title = R.string.sort_by
        )
        val sortByValues = SortBy.values().toList()
        FilterGrid(items = sortByValues) { index, _ ->
            val sortBy = sortByValues[index]
            val selected = sortByState.value == sortBy
            FilterRadioItem(title = stringResource(id = sortBy.titleResId), selected = selected) {
                currentValue = sortBy
                sortByState.value = sortBy
                onChanged()
            }
        }
        FilterSectionDivider()
    }
}
