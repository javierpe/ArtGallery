package com.javi.dynamic.list.domain.repository

import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.dataSources.DynamicListDataSourceApi
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListContainer
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.repositories.DynamicListRepository
import com.javi.dynamic.list.data.useCases.DynamicListRenderProcessorApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val DEFAULT_DELAY: Long = 3000

class DynamicListRepositoryImpl @Inject constructor(
    private val dynamicListRenderProcessorApi: DynamicListRenderProcessorApi,
    private val dynamicListMockResponseApi: DynamicListDataSourceApi
) : DynamicListRepository {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListUIState> = flow {
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

        emit(DynamicListUIState.SuccessAction(container))
    }
}
