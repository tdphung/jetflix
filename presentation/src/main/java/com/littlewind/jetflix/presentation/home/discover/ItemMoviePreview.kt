package com.littlewind.jetflix.presentation.home.discover

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.littlewind.jetflix.common.ui.theme.MyJetFlixTheme
import com.littlewind.jetflix.domain.model.movie.Movie

val dummyMovie = Movie(
    id = 1,
    title = "Beast",
    posterPath = "",
    releaseDate = "2022-08-11",
    voteAverage = 7.1,
    voteCount = 522,
)

@Preview
@Composable
fun MovieFooterPreview() {
    MyJetFlixTheme {
        MovieFooter(
            movie = dummyMovie,
        )
    }
}

@Preview
@Composable
fun ItemMoviePreview() {
    MyJetFlixTheme {
        ItemMovie(
            movie = dummyMovie,
        ) {

        }
    }
}