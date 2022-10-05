package com.littlewind.myjetflix.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.littlewind.jetflix.common.ui.LocalNavController
import com.littlewind.jetflix.presentation.home.discover.DiscoverScreen
import com.littlewind.jetflix.presentation.movie_detail.MovieDetailScreen
import com.littlewind.myjetflix.navigation.Screen

@Composable
fun MainContent() {
    val navController = LocalNavController.current
    NavHost(navController = navController, startDestination = Screen.DISCOVER.route) {
        composable(Screen.DISCOVER.route) {
            DiscoverScreen {
                navController.navigate(Screen.DETAIL.createPath(it.id))
            }
        }
        navigation(startDestination = Screen.DETAIL.route, route = "movie") {
            argument(MovieDetailScreen.ARG_MOVIE_ID) { type = NavType.StringType }

            composable(route = Screen.DETAIL.route) {
                MovieDetailScreen()
            }
        }
    }
}
