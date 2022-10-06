package com.littlewind.jetflix.domain.repository

import com.littlewind.jetflix.domain.model.language.Language
import kotlinx.coroutines.flow.Flow

interface LanguageDataStore {

    val selectedLanguage: Flow<Language>

    suspend fun currentLanguageCode(): String

    suspend fun updateSelectedLanguage(language: Language)
}
