package com.nucu.dynamiclistcompose.controllers

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel

abstract class DynamicListComposeLoader {

    @Composable
    abstract fun <T: DynamicListComposeController> DynamicListScreen(
        bodyAdapterController: T,
        headerAdapterController: T?,
        footerAdapterController: T?,
    )
}