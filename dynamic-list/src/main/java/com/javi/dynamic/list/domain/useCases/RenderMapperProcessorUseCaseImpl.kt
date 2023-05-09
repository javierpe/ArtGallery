package com.javi.dynamic.list.domain.useCases

import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.ContainerModel
import com.javi.dynamic.list.domain.api.RenderMapperProcessor
import com.javi.dynamic.list.domain.api.useCases.RenderMapperProcessorUseCase
import javax.inject.Inject

class RenderMapperProcessorUseCaseImpl @Inject constructor(
    private val renderMapperProcessor: RenderMapperProcessor
) : RenderMapperProcessorUseCase {

    override suspend operator fun invoke(containerModel: ContainerModel): DynamicListFlowState {
        val headers = containerModel.header.mapNotNull { component ->
            renderMapperProcessor.processResource(
                component.render,
                component.resource
            )?.let { res ->
                ComponentItemModel(
                    render = component.render,
                    index = component.index,
                    resource = res,
                    isChanged = false
                )
            }
        }

        val body = containerModel.body.mapNotNull { component ->
            if (component.isChanged) {
                renderMapperProcessor.processResource(
                    component.render,
                    component.resource
                )?.let { res ->
                    ComponentItemModel(
                        render = component.render,
                        index = component.index,
                        resource = res,
                        isChanged = false
                    )
                }
            } else {
                component
            }
        }

        return DynamicListFlowState.MapperResultAction(
            header = headers,
            body = body
        )
    }
}
