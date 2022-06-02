package com.nucu.dynamiclistcompose.models

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState

data class ComponentInfo(
    val windowWidthSizeClass: WindowWidthSizeClass,
    val showCaseState: ShowCaseState,
    val scrollAction: ((ScrollAction) -> Unit)? = null
)
