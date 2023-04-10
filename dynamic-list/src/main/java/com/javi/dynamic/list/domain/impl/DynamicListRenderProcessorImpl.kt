package com.javi.dynamic.list.domain.impl

import com.javi.dynamic.list.data.useCases.DynamicListRenderProcessorApi
import com.javi.dynamic.list.data.renders.base.DynamicListRender
import javax.inject.Inject

class DynamicListRenderProcessorImpl @Inject constructor(
    private val renders: MutableSet<@JvmSuppressWildcards DynamicListRender<*>>
): DynamicListRenderProcessorApi {

    override suspend fun <T> processResource(render: String, resource: T?): Any? {
        return renders.firstOrNull {
            it.renders.any { renderValue -> renderValue.value == render }
        }?.resolve(render, resource) ?: resource
    }
}