package com.javi.dynamic.list.data.dataSources

import com.javi.dynamic.list.data.models.DataContentModel
import retrofit2.http.POST

interface DynamicListRemote {
    @POST("dynamic/context/content")
    suspend fun getContent(): DataContentModel
}