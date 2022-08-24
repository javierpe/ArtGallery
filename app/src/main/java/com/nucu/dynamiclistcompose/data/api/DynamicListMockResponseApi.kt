package com.nucu.dynamiclistcompose.data.api

import com.nucu.dynamiclistcompose.data.models.DataContentModel

interface DynamicListMockResponseApi {

    suspend fun getJsonDataFromAsset(): DataContentModel
}