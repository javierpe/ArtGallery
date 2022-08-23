package com.nucu.dynamiclistcompose.data.factories.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nucu.dynamiclistcompose.data.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.renders.base.RenderType

interface DynamicListFactory {

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
        modifier: Modifier,
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