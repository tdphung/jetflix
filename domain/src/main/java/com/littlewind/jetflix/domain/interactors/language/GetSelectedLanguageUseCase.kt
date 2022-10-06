package com.littlewind.jetflix.domain.interactors.language

import com.littlewind.jetflix.domain.interactors.SubjectInteractor
import com.littlewind.jetflix.domain.model.language.Language
import com.littlewind.jetflix.domain.repository.LanguageDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedLanguageUseCase @Inject constructor(
    private val dataStore: LanguageDataStore
) : SubjectInteractor<Unit, Language>() {
    override fun createObservable(params: Unit): Flow<Language> {
        return dataStore.selectedLanguage
    }
}
