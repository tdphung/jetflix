package com.littlewind.jetflix.presentation.movie_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.littlewind.jetflix.common.ui.theme.imageTint
import com.littlewind.jetflix.domain.model.movie.ProductionCompany
import com.littlewind.jetflix.presentation.R
import com.littlewind.jetflix.presentation.movie_detail.LocalVibrantColor

@Composable
fun ProductionCompany(company: ProductionCompany) {
    Card(
        Modifier
            .width(160.dp)
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(LocalVibrantColor.current.value.copy(alpha = 0.7f))
                .padding(4.dp)
        ) {
            val request = ImageRequest.Builder(LocalContext.current)
                .data(company.logoUrl)
                .crossfade(true)
                .build()
            val painter = rememberAsyncImagePainter(
                model = request,
                placeholder = painterResource(id = R.drawable.ic_jetflix),
                error = rememberVectorPainter(Icons.Default.BrokenImage)
            )
            val colorFilter = when (painter.state) {
                is AsyncImagePainter.State.Error -> ColorFilter.tint(MaterialTheme.colors.imageTint)
                else -> null
            }
            Image(
                painter = painter,
                colorFilter = colorFilter,
                contentDescription = stringResource(
                    id = R.string.production_company_logo_content_description,
                    company.name
                ),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp, 85.dp)
            )
            Text(
                text = company.name,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp)
            )
        }
    }
}