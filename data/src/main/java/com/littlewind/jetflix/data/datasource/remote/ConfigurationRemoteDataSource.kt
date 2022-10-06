package com.littlewind.jetflix.data.datasource.remote

import com.littlewind.jetflix.data.api.MovieApi
import com.littlewind.jetflix.data.entities.language.LanguageResponse
import javax.inject.Inject

class ConfigurationRemoteDataSource @Inject constructor(
    private val api: MovieApi,
) {

    suspend fun fetchLanguages(): List<LanguageResponse> {
        return api.fetchLanguages()
    }
}
