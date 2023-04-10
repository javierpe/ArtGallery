package com.javi.dynamic.list.data.useCases

interface DynamicListRenderProcessorApi {

    suspend fun <T> processResource(render: String, resource: T? = null): Any?
}
