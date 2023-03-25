package com.javi.render.processor.data.models

import com.javi.render.data.RenderType

data class ModelClassProcessed(
    val packageName: String,
    val semanticName: String,
    val simpleName: String,
    val renderType: RenderType
)
