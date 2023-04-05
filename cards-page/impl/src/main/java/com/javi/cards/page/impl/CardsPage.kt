package com.javi.cards.page.impl

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.cards.page.impl.viewModels.CardsPageViewModel
import com.javi.data.DEEPLINK_MAIN
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.dynamic.list.presentation.ui.base.rememberDynamicListRequestState
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination(
    deepLinks = [
        DeepLink(
            uriPattern = "${DEEPLINK_MAIN}cards_page/{id}/{title}/",
        )
    ]
)
@Composable
fun CardsPage(
    id: Int = 0,
    title: String = "",
    destinationsNavigator: DestinationsNavigator,
    viewModel: CardsPageViewModel = hiltViewModel()
) {
    val dynamicListState = rememberDynamicListRequestState {
        viewModel.requestModel.copy(
            state = hashMapOf("id" to id.toString())
        )
    }

    ContextView(
        title = title,
        viewModel = viewModel,
        destinationsNavigator = destinationsNavigator,
        dynamicListRequestModel = dynamicListState.value
    )
}