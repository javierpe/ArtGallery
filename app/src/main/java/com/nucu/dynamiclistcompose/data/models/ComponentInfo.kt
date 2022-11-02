package com.nucu.dynamiclistcompose.data.models

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.nucu.dynamiclistcompose.data.actions.ScrollAction
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState

data class ComponentInfo(
    val windowWidthSizeClass: WindowWidthSizeClass,
    val showCaseState: ShowCaseState,
    val scrollAction: ((ScrollAction) -> Unit)? = null
)
