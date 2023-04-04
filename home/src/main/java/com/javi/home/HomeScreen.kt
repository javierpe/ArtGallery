package com.javi.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.home.viewModels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    ContextView(
        title = "Art Gallery",
        viewModel = viewModel,
        dynamicListRequestModel = viewModel.requestModel
    )
}