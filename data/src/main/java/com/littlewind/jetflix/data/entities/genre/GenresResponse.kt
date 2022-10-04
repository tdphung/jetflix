package com.littlewind.jetflix.data.entities.genre

import com.google.gson.annotations.SerializedName
import com.littlewind.jetflix.domain.model.genre.Genre
import javax.inject.Inject


data class GenresResponse(@SerializedName("genres") val genres: List<GenreResponse>)

data class GenreResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?
)

class GenreResponseMapper @Inject constructor() {
    fun mapToDomain(response: GenreResponse): Genre {
        with(response) {
            return Genre(
                id = id,
                name = name,
            )
        }
    }
}
