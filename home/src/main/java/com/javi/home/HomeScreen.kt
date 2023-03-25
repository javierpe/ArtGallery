package com.javi.home

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.home.viewModels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    widthSizeClass: WindowWidthSizeClass,
    viewModel: MainViewModel = hiltViewModel()
) {
    ContextView(
        title = "Art Gallery",
        widthSizeClass = widthSizeClass,
        navigator = navigator,
        viewModel = viewModel
    )
}