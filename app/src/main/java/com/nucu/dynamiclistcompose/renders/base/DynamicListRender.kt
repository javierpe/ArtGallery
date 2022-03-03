package com.nucu.dynamiclistcompose.renders.base

interface DynamicListRender<T> {

    val renders: List<RenderType>
        get() = listOf()

    suspend fun resolve(render: String, resource: String?, source: String): T
}