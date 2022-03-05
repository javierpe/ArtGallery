package com.nucu.dynamiclistcompose.models

import com.nucu.dynamiclistcompose.renders.base.RenderType

sealed class DynamicListAction {
    object LoadingAction : DynamicListAction()
    class ErrorAction(val exception: Exception): DynamicListAction()
    class SuccessAction(val container: DynamicListContainer): DynamicListAction()
    class SkeletonAction(val renderTypes: List<RenderType>): DynamicListAction()
}