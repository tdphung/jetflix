package com.littlewind.jetflix.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.littlewind.jetflix.domain.model.language.Language
import com.littlewind.jetflix.domain.repository.LanguageDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LanguageDataStoreImpl(
    private val gson: Gson,
    private val preferences: DataStore<Preferences>
) : LanguageDataStore {

    companion object {
        val KEY_LANGUAGE = stringPreferencesKey("language")
    }

    override val selectedLanguage: Flow<Language> = preferences.data
        .map { preferences ->
            val languageString = preferences[KEY_LANGUAGE]
            if (languageString != null) {
                gson.fromJson(languageString, Language::class.java)
            } else {
                Language.default
            }
        }

    override suspend fun currentLanguageCode(): String = selectedLanguage.first().iso6391

    override suspend fun updateSelectedLanguage(language: Language) {
        preferences.edit { preferences ->
            preferences[KEY_LANGUAGE] = gson.toJson(language)
        }
    }
}
