package com.javi.places.page.impl

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.data.DEEPLINK_MAIN
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.places.page.impl.viewModels.PlacesPageViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination(
    route = "places",
    deepLinks = [
        DeepLink(
            uriPattern = "${DEEPLINK_MAIN}places/"
        )
    ]
)
@Composable
fun PlacesPage(
    viewModel: PlacesPageViewModel = hiltViewModel()
) {

    ContextView(
        title = "Places",
        dynamicListRequestModel = viewModel.requestModel,
        viewModel = viewModel
    )
}