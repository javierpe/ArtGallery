package com.javi.dynamic.list.data.actions

import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.domain.models.DynamicListShowCaseModel
import com.javi.dynamic.list.presentation.ui.base.DynamicListState
import com.javi.render.processor.core.RenderType
import java.util.LinkedList
import java.util.Queue

sealed class DynamicListUIState {

    object None : DynamicListUIState()

    /**
     * Show loader view
     */
    object LoadingAction : DynamicListUIState()

    /**
     * Show error view
     */
    class ErrorAction(val exception: Throwable) : DynamicListUIState()

    /**
     * Final state
     */
    class SuccessAction(
        val body: List<DynamicListElement> = emptyList(),
        val header: List<DynamicListElement> = emptyList(),
        val showCaseQueue: Queue<DynamicListShowCaseModel> = LinkedList()
    ) : DynamicListUIState()

    /**
     * Show skeleton
     */
    class SkeletonAction(val renderTypes: List<RenderType>) : DynamicListUIState()

    /**
     * State data from response
     */
    class ResponseAction(
        val body: List<ComponentItemModel> = emptyList(),
        val header: List<ComponentItemModel> = emptyList(),
    ) : DynamicListUIState()
}

internal fun DynamicListUIState.SuccessAction.sendAction(
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
