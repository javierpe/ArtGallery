package com.javi.dynamic.list.domain.impl

import com.javi.dynamic.list.data.actions.DynamicListUIEvents
import com.javi.dynamic.list.data.api.DynamicListControllerApi
import com.javi.dynamic.list.data.api.DynamicListMockResponseApi
import com.javi.dynamic.list.data.api.DynamicListRenderProcessorApi
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListContainer
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val DEFAULT_DELAY: Long = 3000

class DynamicListControllerImpl @Inject constructor(
    private val dynamicListRenderProcessorApi: DynamicListRenderProcessorApi,
    private val dynamicListMockResponseApi: DynamicListMockResponseApi
) : DynamicListControllerApi {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListUIEvents> = flow {

        // Emulate response delay
        delay(DEFAULT_DELAY)

        val componentModel = dynamicListMockResponseApi.getDataFromAsset(requestModel)

        val header = componentModel.header.mapNotNull { component ->

            dynamicListRenderProcessorApi.processResource(
                component.render,
                component.resource
            )?.let {
                ComponentItemModel(
                    render = component.render,
                    index = component.index,
                    resource = it
                )
            }
        }

        val body = componentModel.body.mapNotNull { component ->

            dynamicListRenderProcessorApi.processResource(
                component.render,
                component.resource
            )?.let {
                ComponentItemModel(
                    render = component.render,
                    index = component.index,
                    resource = it
                )
            }
        }

        // Response...
        val container = DynamicListContainer(
            header = header,
            body = body
        )

        emit(DynamicListUIEvents.SuccessAction(container))
    }
}