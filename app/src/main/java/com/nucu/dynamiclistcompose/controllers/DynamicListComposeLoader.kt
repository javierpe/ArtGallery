package com.nucu.dynamiclistcompose.controllers

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.actions.ContextViewAction

abstract class DynamicListComposeLoader {

    @Composable
    abstract fun <T: DynamicListComposeController> DynamicListScreen(
        bodyAdapterController: T,
        headerAdapterController: T,
        action: ContextViewAction?,
        widthSizeClass: WindowWidthSizeClass,
    )
}