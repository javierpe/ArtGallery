package com.nucu.dynamiclistcompose.data.renders.base

import com.google.gson.JsonObject

interface DynamicListRender<T> {

    val renders: List<RenderType>
        get() = listOf()

    suspend fun resolve(render: String, resource: JsonObject?): T
}