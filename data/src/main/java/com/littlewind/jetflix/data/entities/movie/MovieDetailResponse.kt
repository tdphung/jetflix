package com.littlewind.jetflix.data.entities.movie

import com.google.gson.annotations.SerializedName
import com.littlewind.jetflix.data.entities.genre.GenreResponse
import com.littlewind.jetflix.data.entities.genre.GenreResponseMapper
import com.littlewind.jetflix.data.utils.toBackdropUrl
import com.littlewind.jetflix.data.utils.toPosterUrl
import com.littlewind.jetflix.domain.model.movie.MovieDetail
import com.littlewind.jetflix.domain.model.movie.ProductionCompany
import javax.inject.Inject

data class MovieDetailResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<GenreResponse>,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyResponse>,
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("revenue") val revenue: Double,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)

class MovieDetailResponseMapper @Inject constructor(
    private val genreMapper: GenreResponseMapper,
    private val companyMapper: ProductionCompanyResponseMapper,
) {
    fun mapToDomain(response: MovieDetailResponse): MovieDetail {
        with(response) {
            return MovieDetail(
                id = id,
                title = title,
                originalTitle = originalTitle,
                overview = overview,
                tagline = tagline.dropLastWhile { it == '.' },
                backdropUrl = backdropPath.orEmpty().toBackdropUrl(),
                posterUrl = posterPath?.toPosterUrl() ?: "",
                genres = genres.map(genreMapper::mapToDomain),
                releaseDate = releaseDate.orEmpty(),
                voteAverage = voteAverage,
                voteCount = voteCount,
                duration = runtime ?: -1,
                productionCompanies = productionCompanies.map(companyMapper::mapToDomain),
                homepage = homepage
            )
        }
    }
}

data class ProductionCompanyResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String
)

class ProductionCompanyResponseMapper @Inject constructor() {
    fun mapToDomain(response: ProductionCompanyResponse): ProductionCompany {
        with(response) {
            return ProductionCompany(
                id = id,
                name = name,
                logoUrl = logoPath?.toPosterUrl() ?: ""
            )
        }
    }
}

data class SpokenLanguage(
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("name") val name: String
)
