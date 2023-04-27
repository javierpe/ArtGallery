package com.javi.dynamic.list.presentation.factories.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.render.processor.core.RenderType

interface DynamicListFactory {

    /**
     * Compatible render
     */
    val render: RenderType

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
        componentInfo: ComponentInfo
    )

    /**
     * Create skeleton for current composable view.
     */
    @Composable
    fun CreateSkeleton()
}
