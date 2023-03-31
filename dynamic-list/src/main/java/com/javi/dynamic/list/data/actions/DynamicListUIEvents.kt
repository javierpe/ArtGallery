package com.javi.dynamic.list.data.actions

import com.javi.dynamic.list.data.models.DynamicListContainer
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.data.models.DynamicListShowCaseModel
import com.javi.render.data.RenderType
import java.util.*

sealed class DynamicListUIEvents {

    object None: DynamicListUIEvents()

    /**
     * Show loader view
     */
    object LoadingAction : DynamicListUIEvents()

    /**
     * Show error view
     */
    class ErrorAction(val exception: Throwable): DynamicListUIEvents()

    /**
     * Show data from response
     */
    class SuccessAction(
        val container: DynamicListContainer,
        val body: List<DynamicListElement> = emptyList(),
        val header: List<DynamicListElement> = emptyList(),
        val showCaseQueue: Queue<DynamicListShowCaseModel> = LinkedList()
    ): DynamicListUIEvents()

    /**
     * Show skeleton
     */
    class SkeletonAction(val renderTypes: List<RenderType>): DynamicListUIEvents()
}