package com.nucu.dynamiclistcompose.data.actions

import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.data.models.DynamicListContainer
import com.nucu.dynamiclistcompose.data.models.DynamicListElement
import com.nucu.dynamiclistcompose.data.models.DynamicListShowCaseModel
import java.util.*

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
    class SuccessAction(
        val container: DynamicListContainer,
        val body: List<DynamicListElement> = emptyList(),
        val header: List<DynamicListElement> = emptyList(),
        val showCaseQueue: Queue<DynamicListShowCaseModel> = LinkedList()
    ): DynamicListAction()

    /**
     * Show skeleton
     */
    class SkeletonAction(val renderTypes: List<RenderType>): DynamicListAction()
}