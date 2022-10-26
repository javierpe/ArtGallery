package com.nucu.dynamiclistcompose.data.actions

import com.javi.render.processor.RenderType
import com.nucu.dynamiclistcompose.data.models.DynamicListContainer

sealed class DynamicListAction {
    /**
     * Show loader view
     */
    object LoadingAction : DynamicListAction()

    /**
     * Show error view
     */
    class ErrorAction(val exception: Throwable): DynamicListAction()

    /**
     * Show data from response
     */
    class SuccessAction(val container: DynamicListContainer): DynamicListAction()

    /**
     * Show skeleton
     */
    class SkeletonAction(val renderTypes: List<RenderType>): DynamicListAction()
}