package com.javi.places.page

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.data.DEEPLINK_MAIN
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.dynamic.list.presentation.ui.base.rememberDynamicListRequestState
import com.javi.places.page.viewModels.PlacesPageViewModel
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
    widthSizeClass: WindowWidthSizeClass,
    viewModel: PlacesPageViewModel = hiltViewModel()
) {

    val dynamicListRequestModel = rememberDynamicListRequestState {
        viewModel.requestModel
    }

    ContextView(
        title = "Places",
        widthSizeClass = widthSizeClass,
        dynamicListRequestModel = dynamicListRequestModel.value,
        viewModel = viewModel
    )
}