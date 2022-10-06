package com.littlewind.jetflix.domain.interactors.language

import com.littlewind.jetflix.domain.interactors.ResultInteractor
import com.littlewind.jetflix.domain.model.language.Language
import com.littlewind.jetflix.domain.repository.LanguageDataStore
import javax.inject.Inject

class UpdateSelectedLanguageUseCase @Inject constructor(
    private val languageDataStore: LanguageDataStore,
) : ResultInteractor<Language, Unit>() {
    override suspend fun doWork(params: Language) {
        languageDataStore.updateSelectedLanguage(params)
    }
}
