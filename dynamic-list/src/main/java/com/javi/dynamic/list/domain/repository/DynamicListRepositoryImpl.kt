package com.javi.dynamic.list.domain.repository

import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.dataSources.DynamicListDataSourceApi
import com.javi.dynamic.list.data.extensions.toComponentItemModel
import com.javi.dynamic.list.data.models.ContainerModel
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.repositories.DynamicListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DynamicListRepositoryImpl @Inject constructor(
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

        emit(
            DynamicListFlowState.ResponseAction(
                ContainerModel(
                    header = componentModel.header.map {
                        it.toComponentItemModel()
                    },
                    body = componentModel.body.map {
                        it.toComponentItemModel()
                    }
                )
            )
        )
    }
}
