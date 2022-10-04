package com.littlewind.jetflix.data.entities.movie

import com.google.gson.annotations.SerializedName

data class DiscoverMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieResponse>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)