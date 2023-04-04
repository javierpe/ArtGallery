package com.javi.home

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.dynamic.list.presentation.ui.base.rememberDynamicListRequestState
import com.javi.home.viewModels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    widthSizeClass: WindowWidthSizeClass,
    viewModel: MainViewModel = hiltViewModel()
) {
    val dynamicListState = rememberDynamicListRequestState {
        viewModel.requestModel
    }

    ContextView(
        title = "Art Gallery",
        widthSizeClass = widthSizeClass,
        viewModel = viewModel,
        dynamicListRequestModel = dynamicListState.value
    )
}