package com.nucu.dynamiclistcompose.data.api

import com.nucu.dynamiclistcompose.data.actions.DynamicListAction
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import kotlinx.coroutines.flow.Flow

interface DynamicListUseCaseApi {

    suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListAction>
}