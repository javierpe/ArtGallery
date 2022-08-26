package com.nucu.dynamiclistcompose.data.api

import com.google.gson.JsonObject

interface DynamicListRenderProcessorApi {

    suspend fun getResourceByRender(render: String, resource: JsonObject? = null): Any?
}