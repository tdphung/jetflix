package com.littlewind.jetflix.presentation.movie_detail

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.littlewind.jetflix.common.ui.widget.error.ErrorColumn
import com.littlewind.jetflix.common.ui.widget.loading.LoadingColumn
import com.littlewind.jetflix.domain.model.movie.MovieDetail
import com.littlewind.jetflix.presentation.R
import com.littlewind.jetflix.presentation.movie_detail.components.*

object MovieDetailScreen {
    const val ARG_MOVIE_ID = "MOVIE_ID"
}

val LocalVibrantColor =
    compositionLocalOf<Animatable<Color, AnimationVector4D>> { error("No vibrant color defined") }

@Composable
fun MovieDetailScreen() {
    val movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
    val uiState = movieDetailViewModel.uiState.collectAsState().value

    when {
        uiState.loading -> {
            val title = stringResource(id = R.string.fetching_movie_detail)
            LoadingColumn(title)
        }
        uiState.error != null -> {
            ErrorColumn(uiState.error.message.orEmpty())
        }
        uiState.movieDetail != null -> {
            val defaultTextColor = MaterialTheme.colors.onBackground
            val vibrantColor = remember { Animatable(defaultTextColor) }
            CompositionLocalProvider(
                LocalVibrantColor provides vibrantColor,
            ) {
                MovieDetail(uiState.movieDetail)
            }
        }
    }
}

@Composable
fun MovieDetail(movieDetail: MovieDetail) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState())
    ) {
        val (appbar, backdrop, poster, title, originalTitle, genres, specs, rateStars, tagline, overview) = createRefs()
        val (castSection, crewSection, imagesSection, productionCompanies, space) = createRefs()
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)

//        GetVibrantColorFromPoster(movieDetail.posterUrl, LocalVibrantColor.current)
        Backdrop(
            backdropUrl = movieDetail.backdropUrl,
            movieDetail.title,
            Modifier.constrainAs(backdrop) {})
        val posterWidth = 160.dp
        AppBar(
            homepage = movieDetail.homepage,
            modifier = Modifier
                .requiredWidth(posterWidth * 2.2f)
                .constrainAs(appbar) { centerTo(poster) }
                .offset(y = 24.dp)
        )
        Poster(
            movieDetail.posterUrl,
            movieDetail.title,
            Modifier
                .zIndex(17f)
                .width(posterWidth)
                .height(240.dp)
                .constrainAs(poster) {
                    centerAround(backdrop.bottom)
                    linkTo(startGuideline, endGuideline)
                }
        )

        Text(
            text = movieDetail.title,
            style = MaterialTheme.typography.h1.copy(
                fontSize = 26.sp,
                letterSpacing = 3.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(title) {
                    top.linkTo(poster.bottom, 8.dp)
                    linkTo(startGuideline, endGuideline)
                }
        )

        if (movieDetail.title != movieDetail.originalTitle) {
            Text(
                text = "(${movieDetail.originalTitle})",
                style = MaterialTheme.typography.subtitle2.copy(
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(originalTitle) {
                        top.linkTo(title.bottom)
                        linkTo(startGuideline, endGuideline)
                    }
            )
        } else {
            Spacer(
                modifier = Modifier.constrainAs(originalTitle) {
                    top.linkTo(title.bottom)
                    linkTo(startGuideline, endGuideline)
                }
            )
        }

        GenreChips(
            movieDetail.genres.take(4),
            modifier = Modifier.constrainAs(genres) {
                top.linkTo(originalTitle.bottom, 16.dp)
                linkTo(startGuideline, endGuideline)
            }
        )

        MovieFields(
            movieDetail,
            modifier = Modifier.constrainAs(specs) {
                top.linkTo(genres.bottom, 12.dp)
                linkTo(startGuideline, endGuideline)
            }
        )

        RateStars(
            movieDetail.voteAverage,
            modifier = Modifier.constrainAs(rateStars) {
                top.linkTo(specs.bottom, 12.dp)
                linkTo(startGuideline, endGuideline)
            }
        )

        Text(
            text = movieDetail.tagline,
            color = LocalVibrantColor.current.value,
            style = MaterialTheme.typography.body1.copy(
                letterSpacing = 2.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(tagline) {
                    top.linkTo(rateStars.bottom, 32.dp)
                }
        )

        Text(
            text = movieDetail.overview,
            style = MaterialTheme.typography.body2.copy(
                letterSpacing = 2.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(overview) {
                    top.linkTo(tagline.bottom, 8.dp)
                    linkTo(startGuideline, endGuideline)
                }
        )

//        val navController = LocalNavController.current
//        MovieSection(
//            items = cast,
//            headerResId = R.string.cast,
////            onSeeAllClicked = { navController.navigate(Screen.CAST.createPath(movieDetail.id)) }, // TODO
//            itemContent = { item, _ -> Person(item, Modifier.width(140.dp)) },
//            modifier = Modifier.constrainAs(castSection) {
//                top.linkTo(overview.bottom, 16.dp)
//                linkTo(startGuideline, endGuideline)
//            }
//        )
//
//        MovieSection(
//            items = crew,
//            headerResId = R.string.crew,
////            onSeeAllClicked = { navController.navigate(Screen.CREW.createPath(movieDetail.id)) }, // TODO
//            itemContent = { item, _ -> Person(item, Modifier.width(140.dp)) },
//            modifier = Modifier.constrainAs(crewSection) {
//                top.linkTo(castSection.bottom, 16.dp)
//                linkTo(startGuideline, endGuideline)
//            }
//        )
//
//        MovieSection(
//            items = images,
//            headerResId = R.string.images,
////            onSeeAllClicked = {
////                navController.navigate(
////                    Screen.IMAGES.createPath(
////                        movieDetail.id,
////                        0
////                    )
////                )
////            },
//            itemContent = { item, index -> MovieImage(item, index) },
//            modifier = Modifier.constrainAs(imagesSection) {
//                top.linkTo(crewSection.bottom, 16.dp)
//                linkTo(startGuideline, endGuideline)
//            }
//        )

        MovieSection(
            items = movieDetail.productionCompanies,
            headerResId = R.string.production_companies,
            onSeeAllClicked = null,
            itemContent = { item, _ -> ProductionCompany(item) },
            modifier = Modifier.constrainAs(productionCompanies) {
                top.linkTo(overview.bottom, 16.dp)
                linkTo(startGuideline, endGuideline)
            }
        )

        Spacer(
            modifier = Modifier
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .constrainAs(space) { top.linkTo(productionCompanies.bottom) }
        )
    }
}
