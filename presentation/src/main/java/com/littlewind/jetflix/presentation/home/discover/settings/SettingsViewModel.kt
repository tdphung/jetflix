package com.littlewind.jetflix.presentation.home.discover.settings

import androidx.lifecycle.viewModelScope
import com.littlewind.android.base.platform.BaseViewModel
import com.littlewind.jetflix.domain.interactors.language.GetLanguagesUseCase
import com.littlewind.jetflix.domain.interactors.language.GetSelectedLanguageUseCase
import com.littlewind.jetflix.domain.interactors.language.UpdateSelectedLanguageUseCase
import com.littlewind.jetflix.domain.model.language.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase,
    private val updateSelectedLanguageUseCase: UpdateSelectedLanguageUseCase,
) : BaseViewModel() {
    init {
        getSelectedLanguageUseCase(Unit)
    }

    val selectedLanguage = getSelectedLanguageUseCase.flow
    val uiState = MutableStateFlow(UiState())

    fun fetchLanguages() {
        val canFetchLanguages = with(uiState.value) { !showLoading && languages.isEmpty() }
        if (canFetchLanguages) {
            viewModelScope.launch {
                uiState.update { it.copy(showLoading = true) }
                try {
                    getLanguagesUseCase(Unit).collectLatest { languages ->
                        uiState.update {
                            it.copy(
                                showLoading = false,
                                languages = languages.sortedBy(Language::englishName)
                            )
                        }
                    }
                } catch (exception: Exception) {
                    uiState.update { it.copy(showLoading = false) }
                }
            }
        }
    }

    fun selectLanguage(language: Language) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSelectedLanguageUseCase.executeSync(language)
            getSelectedLanguageUseCase(Unit)
        }
    }
}
