package com.littlewind.jetflix.presentation.home.discover.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.littlewind.android.base.functional.ValueCallBack
import com.littlewind.jetflix.common.ui.theme.imageTintExt
import com.littlewind.jetflix.domain.model.movie.Movie
import com.littlewind.jetflix.presentation.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemMovie(modifier: Modifier = Modifier, movie: Movie, onMovieClicked: ValueCallBack<Movie>) {
    Box(modifier = modifier) {
        Card(modifier = Modifier
            .padding(top = 14.dp)
            .fillMaxSize(),
            shape = RoundedCornerShape(size = 8.dp),
            elevation = 8.dp,
            onClick = {
                onMovieClicked(movie)
            }
        ) {
            Box {
                MoviePoster(posterPath = movie.posterPath, movieName = movie.title)
                MovieFooter(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color(0x97000000)),
                    movie = movie,
                )
            }
        }
        MovieRating(
            modifier = Modifier
                .align(Alignment.TopCenter),
            rating = movie.voteAverage
        )
    }

}

@Composable
fun MovieRating(modifier: Modifier = Modifier, rating: Double) {
    Surface(
        modifier = modifier,
        elevation = 12.dp,
        shape = RoundedCornerShape(percent = 50),
    ) {
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(Color.rateColors(rating = rating))
                )
                .padding(horizontal = 10.dp)
        )
    }
}

@Composable
fun Color.Companion.rateColors(rating: Double): List<Color> = remember(rating) {
    when {
        rating <= 4.5 -> listOf(Color(0xffe32d20), Color(0xff9c180e))
        rating < 7 -> listOf(Color(0xffe36922), Color(0xff963d09))
        rating < 8.5 -> listOf(Color(0xff87bf32), Color(0xff578216))
        else -> listOf(Color(0xff34c937), Color(0xff0d750f))
    }
}

@Composable
fun BoxScope.MoviePoster(
    modifier: Modifier = Modifier,
    posterPath: String?,
    movieName: String,
) {
    val painter = rememberAsyncImagePainter(
        model = posterPath,
        error = rememberVectorPainter(Icons.Filled.BrokenImage),
        placeholder = rememberVectorPainter(Icons.Default.Movie),
        contentScale = ContentScale.FillBounds,
    )
    val colorFilter = when (painter.state) {
        is AsyncImagePainter.State.Loading, is AsyncImagePainter.State.Error -> ColorFilter.tint(
            MaterialTheme.colors.imageTintExt
        )
        else -> null
    }

    val scale =
        if (painter.state !is AsyncImagePainter.State.Success) ContentScale.Fit else ContentScale.FillBounds

    Image(
        painter = painter,
        colorFilter = colorFilter,
        contentDescription = stringResource(
            id = R.string.movie_poster_content_description,
            movieName
        ),
        contentScale = scale,
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)
    )
}

@Composable
fun MovieFooter(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        MovieName(name = movie.title)
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            MovieFeature(icon = Icons.Default.DateRange, field = movie.releaseDate)
            MovieFeature(icon = Icons.Default.ThumbUp, field = movie.voteCount.toString())
        }
    }
}


@Composable
private fun MovieName(name: String) = Text(
    text = name,
    style = MaterialTheme.typography.subtitle1.copy(
        color = Color.White,
        letterSpacing = 1.5.sp,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.W500
    ),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis
)

@Composable
private fun MovieFeature(modifier: Modifier = Modifier, icon: ImageVector, field: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = field,
            style = MaterialTheme.typography.subtitle2.copy(
                color = Color.White,
                letterSpacing = 1.5.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W400
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
    }
}