package com.javi.dynamic.list.data.dataSources

import com.javi.dynamic.list.data.models.DataContentModel
import retrofit2.http.POST
import retrofit2.http.Url

interface DynamicListRemote {
    @POST
    suspend fun getContent(@Url url: String): DataContentModel
}