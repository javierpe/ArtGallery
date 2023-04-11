package com.javi.places.page.impl

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.data.DEEPLINK_MAIN
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.dynamic.list.presentation.ui.base.rememberDynamicListRequestState
import com.javi.places.page.impl.viewModels.PlacesPageViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
    destinationsNavigator: DestinationsNavigator,
    viewModel: PlacesPageViewModel = hiltViewModel()
) {
    val requestModel = rememberDynamicListRequestState {
        DynamicListRequestModel(
            contextType = ContextType.PLACES
        )
    }

    ContextView(
        title = "Places",
        viewModel = viewModel,
        destinationsNavigator = destinationsNavigator,
        requestModel = requestModel.value
    )
}
