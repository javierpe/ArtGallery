package com.nucu.dynamiclistcompose.domain.impl

import com.nucu.dynamiclistcompose.data.actions.DynamicListAction
import com.nucu.dynamiclistcompose.data.api.DynamicListControllerApi
import com.nucu.dynamiclistcompose.data.api.DynamicListMockResponseApi
import com.nucu.dynamiclistcompose.data.api.DynamicListRenderProcessorApi
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.models.DynamicListContainer
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
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
    ): Flow<DynamicListAction> = flow {

        // Emulate response delay
        delay(DEFAULT_DELAY)

        val componentModel = dynamicListMockResponseApi.getJsonDataFromAsset()

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
            headers = header,
            bodies = body
        )

        emit(DynamicListAction.SuccessAction(container))
    }
}