package com.javi.dynamic.list.data.api

import com.javi.dynamic.list.data.models.DataContentModel
import com.javi.dynamic.list.data.models.DynamicListRequestModel


interface DynamicListMockResponseApi {

    suspend fun getDataFromAsset(
        dynamicListRequestModel: DynamicListRequestModel
    ): DataContentModel
}