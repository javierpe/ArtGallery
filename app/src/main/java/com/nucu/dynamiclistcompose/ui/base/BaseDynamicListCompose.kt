package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nucu.dynamiclistcompose.ui.components.DynamicListHeaderComponentView
import com.nucu.dynamiclistcompose.viewModels.ContextViewModel

@Composable
fun ContextView(
    title: String,
    viewModel: ContextViewModel,
) {

    /**
     * 1. Change context should be a compose state
     * 2. Reload should be a compose state
     */

    val action by viewModel.contextViewAction.collectAsState()

    DynamicListHeaderComponentView(title = title, onBackPressed = { }) {
        DynamicListCompose(viewModel.requestModel).DynamicListScreen(
            headerAdapterController = viewModel.headerAdapterController,
            bodyAdapterController = viewModel.bodyAdapterController,
            action = action
        )
    }
}