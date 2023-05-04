package com.javi.dynamic.list.presentation.ui.state

import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.domain.models.DynamicListShowCaseModel
import com.javi.dynamic.list.presentation.ui.base.DynamicListState
import com.javi.render.processor.core.RenderType
import java.util.LinkedList
import java.util.Queue

sealed class UIState {

    object None : UIState()

    /**
     * Show loader view
     */
    object LoadingState : UIState()

    /**
     * Show error view
     */
    class ErrorState(val message: String) : UIState()

    /**
     * Final state
     */
    class SuccessState(
        val body: List<DynamicListElement> = emptyList(),
        val header: List<DynamicListElement> = emptyList(),
        val showCaseQueue: Queue<DynamicListShowCaseModel> = LinkedList()
    ) : UIState()

    /**
     * Show skeleton
     */
    class SkeletonState(val renderTypes: List<RenderType>) : UIState()
}

internal fun UIState.SuccessState.sendAction(
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
