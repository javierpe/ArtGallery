package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.renders.base.RenderType

sealed class ScrollAction {

    /**
     * Default action
     */
    object None : ScrollAction()

    /**
     * Scroll to any index.
     */
    class ScrollIndex(val index: Int) : ScrollAction()

    /**
     * Scroll to first known render.
     */
    class ScrollRender(val renderType: RenderType) : ScrollAction()

    /**
     * Scroll to index selected and show tooltip
     */
    class ScrollWithTooltip(
        val renderType: RenderType,
        val message: String,
        val coordinates: LayoutCoordinates,
        val shapeRadius: Dp = 10.dp
        ) : ScrollAction()
}