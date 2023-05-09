package com.javi.dynamic.list.domain.api

interface RenderMapperProcessor {

    suspend fun <T> processResource(render: String, resource: T? = null): Any?
}
