package com.littlewind.jetflix.presentation.home.discover.filter.option

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.littlewind.jetflix.domain.model.genre.Genre
import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import com.littlewind.jetflix.presentation.R
import com.littlewind.jetflix.presentation.home.discover.filter.FilterSectionDivider
import com.littlewind.jetflix.presentation.home.discover.filter.FilterSectionTitle

typealias GenresFilterOption = Pair<List<Genre>, MutableList<Int>>

class GenresOption(override val defaultValue: GenresFilterOption) :
    FilterOption<GenresFilterOption> {
    override var currentValue: GenresFilterOption = defaultValue

    private val selectedGenreIds get() = currentValue.second

    override fun modifyFilterState(filterState: FilterMovieParams) =
        filterState.copy(selectedGenreIds = currentValue.second)

    @Composable
    override fun Render(onChanged: () -> Unit) {
        val (genres, selectedGenreIds) = currentValue
        FilterSectionTitle(
            painter = rememberVectorPainter(image = Icons.Default.Category),
            title = R.string.genres
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(genres.size) { index ->
                val currentGenre = genres[index]
                GenreChip(genre = currentGenre) { selected ->
                    selectedGenreIds.removeAll { it == currentGenre.id }
                    if (selected) {
                        selectedGenreIds.add(currentGenre.id)
                    }
                    currentValue = currentValue.copy(second = selectedGenreIds)
                    onChanged()
                }
            }
        }
        FilterSectionDivider()
    }

    @Composable
    private fun GenreChip(genre: Genre, onClicked: (Boolean) -> Unit) {
        val shape = RoundedCornerShape(percent = 50)
        // I've added only genreId and selectedGenreIds to remember because genres does not change.
        // I also am concerned(trying to reduce) about the slot table memory footprint.
        // IMHO this is the fine tuned option of remembering the genre chip selection state.
        var selected by remember(genre.id, selectedGenreIds) {
            mutableStateOf(genre.id in currentValue.second)
        }
        val modifier = Modifier
            .shadow(animateDpAsState(if (selected) 8.dp else 4.dp).value, shape)
            .background(
                animateColorAsState(if (selected) Color.Blue.copy(alpha = 0.15f) else MaterialTheme.colors.surface)
                    .value
            )
            .border(
                2.dp,
                animateColorAsState(if (selected) Color.Blue else Color.Black).value,
                shape
            )
            .selectable(
                selected,
                onClick = {
                    selected = selected.not()
                    onClicked(selected)
                }
            )
            .padding(horizontal = 10.dp, vertical = 3.dp)

        Text(
            text = genre.name.orEmpty(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body2.copy(fontSize = 17.sp),
            modifier = modifier
        )
    }
}

