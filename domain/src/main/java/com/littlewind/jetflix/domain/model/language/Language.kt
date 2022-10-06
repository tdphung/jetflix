package com.littlewind.jetflix.domain.model.language

import android.annotation.SuppressLint
import java.util.*

data class Language(
    val englishName: String,
    val iso6391: String,
    val name: String
) {
    companion object {
        @SuppressLint("ConstantLocale")
        val default = Language(
            englishName = Locale.getDefault().displayLanguage,
            iso6391 = Locale.getDefault().language,
            name = Locale.getDefault().displayLanguage
        )
    }
}

inline val Language.flagUrl get() = "https://www.unknown.nu/flags/images/$iso6391-100"
