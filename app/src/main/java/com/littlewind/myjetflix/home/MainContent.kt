package com.littlewind.myjetflix.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.littlewind.jetflix.presentation.home.discover.DiscoverScreen
import com.littlewind.myjetflix.navigation.Screen

val LocalNavController = compositionLocalOf<NavHostController> { error("No nav controller") }

@Composable
fun MainContent() {
    val navController = LocalNavController.current
    NavHost(navController = navController, startDestination = Screen.DISCOVER.route) {
        composable(Screen.DISCOVER.route) {
            DiscoverScreen()
        }
    }
}
