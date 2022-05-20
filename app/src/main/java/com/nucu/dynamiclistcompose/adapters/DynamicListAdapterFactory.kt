package com.nucu.dynamiclistcompose.adapters

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.actions.DynamicListComponentAction
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.ScrollAction

interface DynamicListAdapterFactory {

    /**
     * List of compatible renders.
     */
    val renders: List<RenderType>

    /**
     * Create composable view.
     */
    @Composable
    fun CreateComponent(
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentAction: ((ScrollAction) -> Unit)?,
        widthSizeClass: WindowWidthSizeClass,
    )

    /**
     * Create skeleton for current composable view.
     */
    @Composable
    fun CreateSkeleton()
}