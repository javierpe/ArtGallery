package com.javi.dynamic.list.data.actions

import com.javi.dynamic.list.data.models.DynamicListContainer
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.data.models.DynamicListShowCaseModel
import com.javi.render.data.RenderType
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