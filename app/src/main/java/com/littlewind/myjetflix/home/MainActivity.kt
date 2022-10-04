package com.littlewind.myjetflix.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.littlewind.jetflix.common.ui.theme.LocalIsAppInDarkTheme
import com.littlewind.jetflix.common.ui.theme.MyJetFlixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        renderUi()
    }

    private fun renderUi() = setContent {
        val systemTheme = isSystemInDarkTheme()
        val isDarkTheme = rememberSaveable { mutableStateOf(systemTheme) }
        val navController = rememberNavController()

        CompositionLocalProvider(LocalIsAppInDarkTheme provides isDarkTheme) {
            MyJetFlixTheme(darkTheme = LocalIsAppInDarkTheme.current.value) {
                CompositionLocalProvider(LocalNavController provides navController) {
                    MainContent()
                }
            }
        }
    }
}