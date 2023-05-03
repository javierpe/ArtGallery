package com.javi.dynamic.list.data.actions

import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.domain.models.DynamicListShowCaseModel
import com.javi.dynamic.list.presentation.ui.base.DynamicListState
import com.javi.render.processor.core.RenderType
import java.util.LinkedList
import java.util.Queue

sealed class DynamicListFlowState {

    object None : DynamicListFlowState()

    /**
     * Show loader view
     */
    object LoadingAction : DynamicListFlowState()

    /**
     * Show error view
     */
    class ErrorAction(val exception: Throwable) : DynamicListFlowState()

    /**
     * Final state
     */
    class SuccessAction(
        val body: List<DynamicListElement> = emptyList(),
        val header: List<DynamicListElement> = emptyList(),
        val showCaseQueue: Queue<DynamicListShowCaseModel> = LinkedList()
    ) : DynamicListFlowState()

    /**
     * Show skeleton
     */
    class SkeletonAction(val renderTypes: List<RenderType>) : DynamicListFlowState()

    /**
     * State data from response
     */
    class ResponseAction(
        val body: List<ComponentItemModel> = emptyList(),
        val header: List<ComponentItemModel> = emptyList(),
    ) : DynamicListFlowState()
}

internal fun DynamicListFlowState.SuccessAction.sendAction(
    dynamicListListener: (DynamicListState) -> Unit
) {
    if (header.isNotEmpty()) {
        dynamicListListener.invoke(
            DynamicListState.OnHeaderItemsLoaded(
                components = header.map { it.componentItemModel }
            )
        )
    }

    if (body.isNotEmpty()) {
        dynamicListListener.invoke(
            DynamicListState.OnBodyItemsLoaded(
                components = body.map { it.componentItemModel }
            )
        )
    }

    dynamicListListener.invoke(
        DynamicListState.OnContextLoaded
    )
}
