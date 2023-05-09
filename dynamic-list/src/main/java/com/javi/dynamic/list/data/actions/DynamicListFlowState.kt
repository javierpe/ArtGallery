package com.javi.dynamic.list.data.actions

import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.ContainerModel
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.domain.models.DynamicListShowCaseModel
import com.javi.render.processor.core.RenderType
import java.util.LinkedList
import java.util.Queue

sealed class DynamicListFlowState {

    object None : DynamicListFlowState()

    /**
     * Show loader view
     */
    object WithoutSkeletonDataAction : DynamicListFlowState()

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
    class SkeletonDataAction(val renderTypes: List<RenderType>) : DynamicListFlowState()

    class MapperResultAction(
        val body: List<ComponentItemModel> = emptyList(),
        val header: List<ComponentItemModel> = emptyList(),
    ) : DynamicListFlowState()

    /**
     * State data from response
     */
    class ResponseAction(
        val dataContentModel: ContainerModel
    ) : DynamicListFlowState()
}
