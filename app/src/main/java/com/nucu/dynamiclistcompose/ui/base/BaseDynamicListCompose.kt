package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeAction
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.viewModels.BaseDynamicListViewModel

@Composable
fun ContextView(
    bodyComposeController: DynamicListComposeController,
    headerComposeController: DynamicListComposeController? = null,
    footerComposeController: DynamicListComposeController? = null,
    requestModel: DynamicListRequestModel,
    viewModel: BaseDynamicListViewModel = hiltViewModel()
) {
    val isLoadedState = remember {
        mutableStateOf(false)
    }

    val composeAction = remember {
        mutableStateOf<DynamicListComposeAction?>(null)
    }

    if (!isLoadedState.value) {
        composeAction.value = viewModel.load(requestModel)
        isLoadedState.value = true
    }

    composeAction.value?.Load(
        bodyAdapterController = bodyComposeController,
        headerAdapterController = headerComposeController,
        footerAdapterController = footerComposeController
    )
}