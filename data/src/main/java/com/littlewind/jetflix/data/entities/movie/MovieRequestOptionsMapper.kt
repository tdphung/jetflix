package com.littlewind.jetflix.data.entities.movie

import com.littlewind.jetflix.domain.model.movie.FilterMovieParams
import javax.inject.Inject

class MovieRequestOptionsMapper @Inject constructor() {
    fun map(filterState: FilterMovieParams): Map<String, String> = buildMap {
        val sortBy = "${filterState.sortBy.value}.${filterState.sortOrder.value}"
        put(SORT_BY, sortBy)
        val includeAdult = filterState.includeAdult.toString()
        put(INCLUDE_ADULT, includeAdult)
        if (filterState.selectedGenreIds.isNotEmpty()) {
            val selectedGenreIds = filterState.selectedGenreIds.joinToString("|")
            put(WITH_GENRES, selectedGenreIds)
        }
    }

    companion object {
        private const val SORT_BY = "sort_by"
        private const val INCLUDE_ADULT = "include_adult"
        private const val WITH_GENRES = "with_genres"
    }
}