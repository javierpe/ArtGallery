package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.ui.components.DynamicListHeaderComponentView

@Composable
fun ContextView(
    title: String,
    bodyComposeController: DynamicListComposeController,
    headerComposeController: DynamicListComposeController? = null,
    footerComposeController: DynamicListComposeController? = null,
    requestModel: DynamicListRequestModel,
) {
    DynamicListHeaderComponentView(title = title, onBackPressed = { }) {
        DynamicListCompose(requestModel).DynamicListScreen(
            bodyAdapterController = bodyComposeController,
            headerAdapterController = headerComposeController,
            footerAdapterController = footerComposeController
        )
    }
}