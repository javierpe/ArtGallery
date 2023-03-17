package com.nucu.dynamiclistcompose.data.api

import com.nucu.dynamiclistcompose.data.models.DataContentModel
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel

interface DynamicListMockResponseApi {

    suspend fun getDataFromAsset(dynamicListRequestModel: DynamicListRequestModel): DataContentModel
}