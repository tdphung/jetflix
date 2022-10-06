package com.littlewind.jetflix.domain.interactors.language

import com.littlewind.jetflix.domain.interactors.ResultInteractor
import com.littlewind.jetflix.domain.model.language.Language
import com.littlewind.jetflix.domain.repository.ConfigRepository
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
) : ResultInteractor<Unit, List<Language>>() {
    override suspend fun doWork(params: Unit): List<Language> {
        return configRepository.getAllLanguages()
    }
}
