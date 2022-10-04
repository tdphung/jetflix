package com.littlewind.jetflix.domain.model.movie

import androidx.annotation.StringRes
import com.littlewind.jetflix.domain.R

data class FilterMovieParams(
    val sortOrder: SortOrder = SortOrder.DESCENDING,
    val sortBy: SortBy = SortBy.POPULARITY,
    val includeAdult: Boolean = false,
    val selectedGenreIds: List<Int> = emptyList(),
)

enum class SortOrder(@StringRes val titleResId: Int, val value: String) {
    DESCENDING(R.string.sort_order_descending, "desc"),
    ASCENDING(R.string.sort_order_ascending, "asc")
}

enum class SortBy(@StringRes val titleResId: Int, val value: String) {
    POPULARITY(R.string.sort_by_popularity, "popularity"),
    VOTE_COUNT(R.string.sort_by_vote_count, "vote_count"),
    VOTE_AVERAGE(R.string.sort_by_vote_average, "vote_average"),
    RELEASE_DATE(R.string.sort_by_release_date, "release_date"),
    ORIGINAL_TITLE(R.string.sort_by_original_title, "original_title"),
    REVENUE(R.string.sort_by_revenue, "revenue")
}
