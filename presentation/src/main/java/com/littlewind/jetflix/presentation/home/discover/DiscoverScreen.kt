package com.littlewind.jetflix.presentation.home.discover

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.littlewind.jetflix.presentation.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiscoverScreen(
    isDarkTheme: MutableState<Boolean>
) {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Text(
                "Bottom Sheet",
                modifier = Modifier.padding(vertical = 50.dp),
            )
        },
    ) {
        DiscoverScreenContent(isDarkTheme, sheetState)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DiscoverScreenContent(
    isDarkTheme: MutableState<Boolean>,
    bottomSheetState: ModalBottomSheetState
) {
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .wrapContentSize()
                    .navigationBarsPadding(),
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                },
                content = {
                    val color =
                        if (isDarkTheme.value) MaterialTheme.colors.surface else MaterialTheme.colors.onPrimary
                    val tint = animateColorAsState(color).value
                    Image(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = stringResource(id = R.string.title_filter_bottom_sheet),
                        colorFilter = ColorFilter.tint(tint)
                    )
                }
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                MoviesGrid(moviesViewModel)
            }
        }
    )
}