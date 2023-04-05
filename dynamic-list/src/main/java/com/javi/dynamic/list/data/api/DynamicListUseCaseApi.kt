package com.javi.dynamic.list.data.api

import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import kotlinx.coroutines.flow.Flow

interface DynamicListUseCaseApi {
    suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel,
        withSkeletons: Boolean = true
    ): Flow<DynamicListUIState>
}