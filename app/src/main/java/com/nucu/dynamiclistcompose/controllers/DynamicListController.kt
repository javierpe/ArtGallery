package com.nucu.dynamiclistcompose.controllers

import com.nucu.dynamiclistcompose.models.DynamicListContainer
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel

interface DynamicListController {

    suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel,
    ): DynamicListContainer
}