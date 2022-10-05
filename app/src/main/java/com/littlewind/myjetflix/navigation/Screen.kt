package com.littlewind.myjetflix.navigation

import com.littlewind.jetflix.presentation.movie_detail.MovieDetailScreen

//const val ARG_INITIAL_PAGE = "initialPage"

enum class Screen(val route: String) {
    DISCOVER("movies"),
    DETAIL("movie/{${MovieDetailScreen.ARG_MOVIE_ID}}/detail");
//    IMAGES("movie/{$ARG_MOVIE_ID}/images?$ARG_INITIAL_PAGE={$ARG_INITIAL_PAGE}"),
//    CAST("movie/{$ARG_MOVIE_ID}/cast"),
//    CREW("movie/{$ARG_MOVIE_ID}/crew");

    fun createPath(vararg args: Any): String {
        var route = route
        require(args.size == route.argumentCount) {
            "Provided ${args.count()} parameters, was expected ${route.argumentCount} parameters!"
        }
        route.arguments().forEachIndexed { index, matchResult ->
            route = route.replace(matchResult.value, args[index].toString())
        }
        return route
    }
}
