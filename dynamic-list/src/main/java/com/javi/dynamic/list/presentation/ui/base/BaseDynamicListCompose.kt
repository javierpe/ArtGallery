package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.design.system.molecules.headers.DynamicListHeaderComponentView
import com.javi.design.system.molecules.showCase.ShowCase
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.actions.ContextViewAction
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.presentation.viewModels.ContextViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ContextView(
    title: String,
    destinationsNavigator: DestinationsNavigator? = null,
    dynamicListRequestModel: DynamicListRequestModel,
    viewModel: ContextViewModel
) {

    val action by viewModel.contextViewAction.collectAsStateWithLifecycle(
        initialValue = ContextViewAction.None
    )

    val showCaseState = rememberShowCaseState()

    val bodyLazyListState = rememberLazyListState()

    ShowCase(
        showIntroShowCase = true,
        state = showCaseState
    ) {
        Scaffold(
            topBar = {
                if (title.isNotEmpty()) {
                    DynamicListHeaderComponentView(
                        title = title,
                        contextType = viewModel.context,
                        bodyLazyListState = bodyLazyListState,
                        destinationsNavigator = destinationsNavigator
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                ContextViewContent(
                    headerAdapterController = viewModel.headerAdapterController,
                    bodyAdapterController = viewModel.bodyAdapterController,
                    action = action,
                    showCaseState = showCaseState,
                    bodyListState = bodyLazyListState,
                    requestModel = dynamicListRequestModel,
                    dynamicListListener = {

                    }
                )
            }
        }
    }
}
