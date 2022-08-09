package com.nucu.dynamiclistcompose.controllers

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.actions.ContextViewAction
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState

abstract class DynamicListComposeLoader {

    @Composable
    abstract fun <T: DynamicListComposeController> DynamicListScreen(
        bodyAdapterController: T,
        headerAdapterController: T,
        action: ContextViewAction?,
        widthSizeClass: WindowWidthSizeClass,
        showCaseState: ShowCaseState,
        bodyListState: LazyListState
    )
}