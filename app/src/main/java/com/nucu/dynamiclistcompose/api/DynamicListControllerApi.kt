package com.nucu.dynamiclistcompose.api

import com.nucu.dynamiclistcompose.actions.DynamicListAction
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import kotlinx.coroutines.flow.Flow

interface DynamicListControllerApi {

    suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel,
    ): Flow<DynamicListAction>
}