package com.littlewind.jetflix.presentation.home.discover.filter.option

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.domain.model.movie.SortOrder
import com.littlewind.jetflix.presentation.R
import com.littlewind.jetflix.presentation.home.discover.filter.FilterGrid
import com.littlewind.jetflix.presentation.home.discover.filter.FilterRadioItem
import com.littlewind.jetflix.presentation.home.discover.filter.FilterSectionDivider
import com.littlewind.jetflix.presentation.home.discover.filter.FilterSectionTitle

data class SortOrderOption(override val defaultValue: SortOrder) : FilterOption<SortOrder> {
    override var currentValue: SortOrder = defaultValue

    override fun modifyFilterState(filterState: FilterMovieParams) = filterState.copy(sortOrder = currentValue)

    @Composable
    override fun Render(onChanged: () -> Unit) {
        val sortOrderState = remember(defaultValue) { mutableStateOf(currentValue) }
        FilterSectionTitle(
            painter = rememberVectorPainter(image = Icons.Default.SwapVert),
            title = R.string.sort_order
        )
        val sortOrderValues = SortOrder.values().toList()
        FilterGrid(items = sortOrderValues) { index, _ ->
            val sortOrder = sortOrderValues[index]
            val selected = sortOrderState.value == sortOrder
            FilterRadioItem(title = stringResource(id = sortOrder.titleResId), selected = selected) {
                currentValue = sortOrder
                sortOrderState.value = currentValue
                onChanged()
            }
        }
        FilterSectionDivider()
    }
}
