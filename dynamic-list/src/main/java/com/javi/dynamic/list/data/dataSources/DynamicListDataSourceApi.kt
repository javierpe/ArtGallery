package com.javi.dynamic.list.data.dataSources

import com.javi.dynamic.list.data.models.DataContentModel
import com.javi.dynamic.list.data.models.DynamicListRequestModel

interface DynamicListDataSourceApi {

    suspend fun getDataFromAsset(
        dynamicListRequestModel: DynamicListRequestModel
    ): DataContentModel

    suspend fun getRemoteData(
        dynamicListRequestModel: DynamicListRequestModel
    ): DataContentModel
}
