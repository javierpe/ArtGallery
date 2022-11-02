package com.nucu.dynamiclistcompose.data.api

interface DynamicListRenderProcessorApi {

    suspend fun <T> processResource(render: String, resource: T? = null): Any?
}