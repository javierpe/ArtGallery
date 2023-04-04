package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.javi.design.system.molecules.headers.DynamicListHeaderComponentView
import com.javi.design.system.molecules.showCase.ShowCase
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.actions.ContextViewAction
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.presentation.viewModels.ContextViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContextView(
    title: String,
    dynamicListRequestModel: DynamicListRequestModel,
    viewModel: ContextViewModel
) {

    val action by viewModel.contextViewAction.collectAsStateWithLifecycle(
        initialValue = ContextViewAction.None
    )

    val showCaseState = rememberShowCaseState()

    val bodyLazyListState = rememberLazyListState()

    val navController = rememberAnimatedNavController()


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
                        bodyLazyListState = bodyLazyListState
                    ) {
                        navController.popBackStack()
                    }
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
