package com.littlewind.jetflix.data.entities.language

import com.google.gson.annotations.SerializedName
import com.littlewind.jetflix.domain.model.language.Language
import javax.inject.Inject

data class LanguageResponse(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("name") val name: String
)

class LanguageResponseMapper @Inject constructor() {
    fun mapToDomain(response: LanguageResponse): Language {
        with(response) {
            return Language(
                englishName = englishName,
                iso6391 = iso6391,
                name = name,
            )
        }
    }
}
