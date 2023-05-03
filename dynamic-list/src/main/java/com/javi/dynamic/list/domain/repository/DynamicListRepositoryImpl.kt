package com.javi.dynamic.list.domain.repository

import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.dataSources.DynamicListDataSourceApi
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.repositories.DynamicListRepository
import com.javi.dynamic.list.data.useCases.DynamicListRenderProcessorApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DynamicListRepositoryImpl @Inject constructor(
    private val dynamicListRenderProcessorApi: DynamicListRenderProcessorApi,
    private val dynamicListMockResponseApi: DynamicListDataSourceApi
) : DynamicListRepository {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel,
        fromRemote: Boolean
    ): Flow<DynamicListFlowState> = flow {
        val componentModel = if (fromRemote) {
            dynamicListMockResponseApi.getRemoteData(requestModel)
        } else {
            dynamicListMockResponseApi.getDataFromAsset(requestModel)
        }

        val headers = componentModel.header.mapNotNull { component ->

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

        emit(
            DynamicListFlowState.ResponseAction(
                header = headers,
                body = body
            )
        )
    }
}
