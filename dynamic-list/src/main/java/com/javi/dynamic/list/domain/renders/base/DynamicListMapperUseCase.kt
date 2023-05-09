package com.javi.dynamic.list.domain.renders.base

import com.javi.render.processor.core.RenderType

interface DynamicListMapperUseCase<out T> {

    val render: RenderType

    suspend fun <T> resolve(render: String, resource: T?): Any?
}
