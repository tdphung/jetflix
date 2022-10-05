package com.littlewind.jetflix.presentation.movie_detail.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.littlewind.jetflix.common.ui.animation.springAnimation
import com.littlewind.jetflix.presentation.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Poster(posterUrl: String, movieName: String, modifier: Modifier) {
    val isScaled = remember { mutableStateOf(false) }
    val scale =
        animateFloatAsState(targetValue = if (isScaled.value) 2.2f else 1f, animationSpec = springAnimation).value

    Card(
        elevation = 24.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.scale(scale),
        onClick = { isScaled.value = !isScaled.value }
    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = stringResource(id = R.string.movie_poster_content_description, movieName),
            contentScale = ContentScale.FillHeight
        )
    }
}