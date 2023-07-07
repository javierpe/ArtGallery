package com.javi.dynamic.list.data.repositories

import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import kotlinx.coroutines.flow.Flow

interface DynamicListRepository {

    suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel,
        fromRemote: Boolean = false // TO-DO: !isMacroBenchmarkEnabled()
    ): Flow<DynamicListFlowState>
}
