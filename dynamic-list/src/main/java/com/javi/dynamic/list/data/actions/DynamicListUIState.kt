package com.javi.dynamic.list.data.actions

import com.javi.dynamic.list.data.models.DynamicListContainer
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.data.models.DynamicListShowCaseModel
import com.javi.render.processor.core.RenderType
import java.util.*

sealed class DynamicListUIState {

    object None: DynamicListUIState()

    /**
     * Show loader view
     */
    object LoadingAction : DynamicListUIState()

    /**
     * Show error view
     */
    class ErrorAction(val exception: Throwable): DynamicListUIState()

    /**
     * Show data from response
     */
    class SuccessAction(
        val container: DynamicListContainer,
        val body: List<DynamicListElement> = emptyList(),
        val header: List<DynamicListElement> = emptyList(),
        val showCaseQueue: Queue<DynamicListShowCaseModel> = LinkedList()
    ): DynamicListUIState()

    /**
     * Show skeleton
     */
    class SkeletonAction(val renderTypes: List<RenderType>): DynamicListUIState()
}