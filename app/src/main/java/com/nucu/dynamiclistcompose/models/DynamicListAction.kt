package com.nucu.dynamiclistcompose.models

import com.nucu.dynamiclistcompose.renders.base.RenderType

sealed class DynamicListAction {
    /**
     * Show loader view
     */
    object LoadingAction : DynamicListAction()

    /**
     * Show error view
     */
    class ErrorAction(val exception: Exception): DynamicListAction()

    /**
     * Show data from response
     */
    class SuccessAction(val container: DynamicListContainer): DynamicListAction()

    /**
     * Show skeleton
     */
    class SkeletonAction(val renderTypes: List<RenderType>): DynamicListAction()
}