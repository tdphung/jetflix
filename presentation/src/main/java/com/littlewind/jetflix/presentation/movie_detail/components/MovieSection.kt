package com.littlewind.jetflix.presentation.movie_detail.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.littlewind.jetflix.presentation.R
import com.littlewind.jetflix.presentation.movie_detail.LocalVibrantColor

@Composable
fun <T : Any> MovieSection(
    items: List<T>,
    @StringRes headerResId: Int,
    onSeeAllClicked: (() -> Unit)? = null,
    itemContent: @Composable (T, Int) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SectionHeader(headerResId, items.size, onSeeAllClicked)
        LazyRow(
            modifier = Modifier.testTag(LocalContext.current.getString(headerResId)),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                count = items.size,
                itemContent = { index ->
                    itemContent(items[index], index)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        }
    }
}

@Composable
private fun SectionHeader(
    @StringRes headerResId: Int,
    count: Int,
    onClick: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(headerResId),
            color = LocalVibrantColor.current.value,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
        if (onClick != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(onClick = { onClick() })
                    .padding(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.see_all, count),
                    color = LocalVibrantColor.current.value,
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.see_all),
                    tint = LocalVibrantColor.current.value
                )
            }
        }
    }
}
