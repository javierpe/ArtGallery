package com.nucu.dynamiclistcompose.data.models

import com.nucu.dynamiclistcompose.data.actions.ScrollAction
import com.javi.design.system.molecules.showCase.ShowCaseState

data class ComponentInfo(
    val dynamicListObject: DynamicListObject,
    val showCaseState: ShowCaseState,
    val scrollAction: ((ScrollAction) -> Unit)? = null
)
