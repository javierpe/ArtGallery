package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nucu.dynamiclistcompose.ui.components.DynamicListHeaderComponentView
import com.nucu.dynamiclistcompose.ui.components.showCase.IntroShowCaseScaffold
import com.nucu.dynamiclistcompose.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.viewModels.ContextViewModel

@Composable
fun ContextView(
    title: String,
    widthSizeClass: WindowWidthSizeClass,
    viewModel: ContextViewModel,
) {

    /**
     * 1. Change context should be a compose state.
     * 2. Reload should be a compose state.
     * 3. Listen scroll progress to show header animation.
     * 4. Implement Scaffold component
     */

    val action by viewModel.contextViewAction.collectAsState()

    val showCaseState = rememberShowCaseState()

    IntroShowCaseScaffold(
        showIntroShowCase = true,
        state = showCaseState,
        onShowCaseCompleted = {
        },
    ) {
        Scaffold(
            topBar = {
                DynamicListHeaderComponentView(
                    title = title,
                    contextType = viewModel.context,
                    onBackPressed = { }
                )
            }
        ) {
            DynamicListCompose(viewModel.requestModel).DynamicListScreen(
                headerAdapterController = viewModel.headerAdapterController,
                bodyAdapterController = viewModel.bodyAdapterController,
                action = action,
                widthSizeClass = widthSizeClass,
                showCaseScope = this,
                showCaseState = showCaseState
            )
        }
    }
}