package com.littlewind.jetflix.presentation.home.discover

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.littlewind.android.base.functional.VoidCallBack
import com.littlewind.jetflix.common.ui.theme.LocalIsAppInDarkTheme
import com.littlewind.jetflix.presentation.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiscoverScreen() {

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
        DiscoverScreenContent(sheetState)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DiscoverScreenContent(
    bottomSheetState: ModalBottomSheetState
) {
    val currentTheme = LocalIsAppInDarkTheme.current
    val isDarkTheme = currentTheme.value
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), elevation = 16.dp) {
                Column(
                    Modifier
                        .background(MaterialTheme.colors.surface)
                        .padding(bottom = 2.dp)
                ) {
                    JetflixAppBar(isDarkTheme = isDarkTheme,
                        onClickSetting = {

                        },
                        onToggleTheme = {
                            coroutineScope.launch {
                                currentTheme.let {
                                    it.value = !it.value
                                }
                            }
                        },
                    )
                }
            }
        },
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
                        if (isDarkTheme) MaterialTheme.colors.surface else MaterialTheme.colors.onPrimary
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


@Composable
private fun JetflixAppBar(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onClickSetting: VoidCallBack,
    onToggleTheme: VoidCallBack,
) {
    val colors = MaterialTheme.colors
    val tint =
        animateColorAsState(if (isDarkTheme) colors.onSurface else colors.primary).value
    Row(
        modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onClickSetting) {
            Icon(
                Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.settings_content_description),
                tint = tint
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_jetflix),
            contentDescription = stringResource(id = R.string.app_name),
            tint = tint,
            modifier = Modifier.size(82.dp)
        )

        val icon = if (isDarkTheme) Icons.Default.NightsStay else Icons.Default.WbSunny
        IconButton(onClick = onToggleTheme) {
            val contentDescriptionResId = if (isDarkTheme) {
                R.string.light_theme_content_description
            } else {
                R.string.dark_theme_content_description
            }
            Icon(
                icon,
                contentDescription = stringResource(id = contentDescriptionResId),
                tint = tint
            )
        }
    }
}
