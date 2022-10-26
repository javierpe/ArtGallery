package com.nucu.dynamiclistcompose.presentation.components.message

import com.javi.render.processor.annotations.render.RenderClass
import com.javi.render.processor.data.enums.RenderType
import com.squareup.moshi.Json

@RenderClass(type = RenderType.MESSAGE)
data class MessageModel(
    @Json(name = "message") val message: String
)