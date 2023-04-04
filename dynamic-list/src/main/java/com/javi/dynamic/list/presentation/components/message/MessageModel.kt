package com.javi.dynamic.list.presentation.components.message

import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderClass
import com.squareup.moshi.Json

@RenderClass(type = RenderType.MESSAGE)
data class MessageModel(
    @Json(name = "message") val message: String
)