package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeLoader
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel

@Composable
fun ContextView(
    bodyComposeController: DynamicListComposeController,
    headerComposeController: DynamicListComposeController? = null,
    footerComposeController: DynamicListComposeController? = null,
    requestModel: DynamicListRequestModel,
) {
    val isLoadedState = remember {
        mutableStateOf(false)
    }

    val composeAction = remember {
        mutableStateOf<DynamicListComposeLoader?>(null)
    }

    if (!isLoadedState.value) {
        composeAction.value = DynamicListCompose(requestModel)
        isLoadedState.value = true
    }

    composeAction.value?.DynamicListScreen(
        bodyAdapterController = bodyComposeController,
        headerAdapterController = headerComposeController,
        footerAdapterController = footerComposeController
    )
}