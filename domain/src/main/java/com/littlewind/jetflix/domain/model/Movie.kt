package com.littlewind.jetflix.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int
)