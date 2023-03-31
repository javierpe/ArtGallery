package com.javi.dynamic.list.data.api

import com.javi.dynamic.list.data.actions.DynamicListUIEvents
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import kotlinx.coroutines.flow.Flow

interface DynamicListControllerApi {

    suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel,
    ): Flow<DynamicListUIEvents>
}