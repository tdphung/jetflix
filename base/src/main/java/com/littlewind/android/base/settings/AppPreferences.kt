package com.littlewind.android.base.settings

import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    fun setup()

    var theme: Theme
    fun observeTheme(): Flow<Theme>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }
}