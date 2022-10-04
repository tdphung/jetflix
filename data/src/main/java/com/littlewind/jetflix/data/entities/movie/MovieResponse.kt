package com.littlewind.jetflix.data.entities.movie

import com.google.gson.annotations.SerializedName
import com.littlewind.jetflix.data.utils.toPosterUrl
import com.littlewind.jetflix.domain.model.movie.Movie
import javax.inject.Inject

data class MovieResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)

class MovieResponseMapper @Inject constructor() {
    fun mapToDomain(response: MovieResponse): Movie {
        with(response) {
            return Movie(
                id = id,
                title = title,
                releaseDate = releaseDate ?: "",
                posterPath = posterPath?.toPosterUrl(),
                voteAverage = voteAverage,
                voteCount = voteCount,
            )
        }
    }
}