package com.nucu.dynamiclistcompose.controllers

import com.nucu.dynamiclistcompose.models.DynamicListAction
import com.nucu.dynamiclistcompose.models.DynamicListContainer
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import kotlinx.coroutines.flow.Flow

interface DynamicListController {

    suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel,
    ): Flow<DynamicListAction>
}