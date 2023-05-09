package com.javi.dynamic.list.domain.impl

import com.javi.dynamic.list.domain.api.RenderMapperProcessor
import com.javi.dynamic.list.domain.renders.base.DynamicListMapperUseCase
import javax.inject.Inject

class RenderMapperProcessorImpl @Inject constructor(
    private val renders: MutableSet<@JvmSuppressWildcards DynamicListMapperUseCase<*>>
) : RenderMapperProcessor {

    override suspend fun <T> processResource(render: String, resource: T?): Any? {
        return renders.firstOrNull {
            it.render.value == render
        }?.resolve(render, resource) ?: resource
    }
}
