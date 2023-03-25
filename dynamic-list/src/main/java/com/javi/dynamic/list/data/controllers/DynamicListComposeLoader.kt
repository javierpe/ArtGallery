package com.javi.dynamic.list.data.controllers

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.javi.dynamic.list.data.actions.ContextViewAction
import com.javi.dynamic.list.data.models.DynamicListObject

abstract class DynamicListComposeLoader {

    @Suppress("LongParameterList")
    @Composable
    abstract fun <T: DynamicListComposeController> DynamicListScreen(
        bodyAdapterController: T,
        headerAdapterController: T,
        action: ContextViewAction?,
        dynamicListObject: DynamicListObject,
        showCaseState: ShowCaseState,
        bodyListState: LazyListState
    )
}