package com.littlewind.jetflix.domain.repository

import com.littlewind.jetflix.domain.model.language.Language

interface ConfigRepository {
    suspend fun getAllLanguages(): List<Language>
}
