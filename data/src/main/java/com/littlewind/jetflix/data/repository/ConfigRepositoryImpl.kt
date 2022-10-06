package com.littlewind.jetflix.data.repository

import com.littlewind.jetflix.data.datasource.remote.ConfigurationRemoteDataSource
import com.littlewind.jetflix.data.entities.language.LanguageResponseMapper
import com.littlewind.jetflix.domain.model.language.Language
import com.littlewind.jetflix.domain.repository.ConfigRepository
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val remoteDataSource: ConfigurationRemoteDataSource,
    private val languageMapper: LanguageResponseMapper,
) : ConfigRepository {
    override suspend fun getAllLanguages(): List<Language> {
        return remoteDataSource.fetchLanguages().map(languageMapper::mapToDomain)
    }
}
