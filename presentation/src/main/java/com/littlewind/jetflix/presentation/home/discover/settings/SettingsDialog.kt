package com.littlewind.jetflix.presentation.home.discover.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.littlewind.android.base.functional.VoidCallBack
import com.littlewind.jetflix.common.ui.widget.loading.LoadingRow
import com.littlewind.jetflix.domain.model.language.Language
import com.littlewind.jetflix.domain.model.language.flagUrl
import com.littlewind.jetflix.presentation.R

@Composable
fun SettingDialog(onDismiss: VoidCallBack) {
    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    LaunchedEffect(true) {
        settingsViewModel.fetchLanguages()
    }
    val uiState = settingsViewModel.uiState.collectAsState()
    val selectedLanguage =
        settingsViewModel.selectedLanguage.collectAsState(initial = Language.default)
    SettingDialogContent(
        uiState = uiState,
        selectedLanguage = selectedLanguage,
        onDialogDismiss = onDismiss,
        onLanguageSelected = { settingsViewModel.selectLanguage(it) }
    )
}

data class UiState(val showLoading: Boolean = false, val languages: List<Language> = emptyList())

@Composable
fun SettingDialogContent(
    uiState: State<UiState>,
    selectedLanguage: State<Language>,
    onLanguageSelected: (Language) -> Unit,
    onDialogDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDialogDismiss) {
        Card(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                if (uiState.value.showLoading) {
                    LoadingRow(title = stringResource(R.string.fetching_languages))
                } else {
                    LanguageRow(uiState.value.languages, selectedLanguage.value, onLanguageSelected)
                }
            }
        }
    }
}

@Composable
private fun LanguageRow(
    languages: List<Language>,
    selectedLanguage: Language,
    onLanguageSelected: (Language) -> Unit
) {
    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
        var showDropdown by remember { mutableStateOf(false) }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.language))
            CurrentLanguage(
                modifier = Modifier.padding(start = 16.dp),
                language = selectedLanguage
            ) {
                showDropdown = true
            }
        }
        DropdownMenu(
            expanded = showDropdown,
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            onDismissRequest = { showDropdown = false }
        ) {
            languages.forEach { language ->
                val selected = language == selectedLanguage
                DropdownItem(language.englishName, language.flagUrl, selected) {
                    onLanguageSelected(language)
                    showDropdown = false
                }
            }
        }
    }
}

private const val DROPDOWN_ID = "dropdownIcon"
private const val FLAG_ID = "flag"
private const val TICK_ID = "tickIcon"

@Composable
private fun CurrentLanguage(
    modifier: Modifier = Modifier,
    language: Language,
    onClick: () -> Unit
) {
    val flagContent = flagContent(language.flagUrl)
    val arrowContent = iconContent(DROPDOWN_ID, Icons.Default.ArrowDropDown)
    Text(
        text = buildAnnotatedString {
            appendInlineContent(FLAG_ID)
            append("  ${language.englishName}")
            appendInlineContent(DROPDOWN_ID)
        },
        inlineContent = mapOf(arrowContent, flagContent),
        modifier = modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun DropdownItem(
    countryName: String,
    flagUrl: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    DropdownMenuItem(enabled = !selected, onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = buildAnnotatedString {
                if (selected) {
                    appendInlineContent(TICK_ID)
                }
                appendInlineContent(FLAG_ID)
                append("  $countryName")
            },
            inlineContent = inlineContent(flagUrl, selected)
        )
    }
}

private fun inlineContent(flagUrl: String, selected: Boolean): Map<String, InlineTextContent> {
    val flagContent = flagContent(flagUrl)
    return if (selected) mapOf(iconContent(TICK_ID, Icons.Default.Done), flagContent) else mapOf(
        flagContent
    )
}

private val placeholder = Placeholder(
    width = 2.5.em,
    height = 1.5.em,
    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
)

private fun iconContent(id: String, icon: ImageVector) = id to InlineTextContent(
    placeholder = placeholder,
    children = {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
        )
    }
)

private fun flagContent(flagUrl: String) = FLAG_ID to InlineTextContent(
    placeholder = placeholder,
    children = {
        AsyncImage(
            model = flagUrl,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )
    }
)
