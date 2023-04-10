package com.javi.home.impl

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.home.impl.viewModels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    ContextView(
        title = "Art Gallery",
        viewModel = viewModel,
        destinationsNavigator = destinationsNavigator,
        dynamicListRequestModel = viewModel.requestModel
    )
}