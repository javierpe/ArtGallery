package com.javi.dynamic.list.data.renders.base

import com.javi.render.processor.core.RenderType

interface DynamicListRender<out T> {

    val renders: List<RenderType>
        get() = listOf()

    suspend fun <T> resolve(render: String, resource: T?): Any?
}