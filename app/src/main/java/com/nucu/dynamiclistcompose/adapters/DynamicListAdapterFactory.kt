package com.nucu.dynamiclistcompose.adapters

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.actions.DynamicListComponentAction
import com.nucu.dynamiclistcompose.models.ComponentInfo
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseScope
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState

interface DynamicListAdapterFactory {

    /**
     * List of compatible renders.
     */
    val renders: List<RenderType>

    /**
     * This render should show a show case.
     */
    val hasShowCaseConfigured: Boolean
        get() = false

    /**
     * Create composable view.
     */
    @Composable
    fun CreateComponent(
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentInfo: ComponentInfo
    )

    /**
     * Create skeleton for current composable view.
     */
    @Composable
    fun CreateSkeleton()
}