package com.javi.render.processor

data class ModelClassProcessed(
    val packageName: String,
    val semanticName: String,
    val simpleName: String,
    val renderType: RenderType
)
