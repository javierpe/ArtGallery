package com.nucu.dynamiclistcompose.adapters

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
import com.nucu.dynamiclistcompose.renders.base.RenderType

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
        componentAction: DynamicListComponentAction
    )

    /**
     * Create skeleton for current composable view.
     */
    @Composable
    fun CreateSkeleton()
}