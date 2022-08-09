package com.nucu.dynamiclistcompose.adapters

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.renders.base.RenderType

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