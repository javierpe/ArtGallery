package com.javi.dynamic.list.domain.api.useCases

import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.ContainerModel

interface RenderMapperProcessorUseCase {

    suspend operator fun invoke(containerModel: ContainerModel): DynamicListFlowState
}
