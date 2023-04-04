package com.javi.dynamic.list.presentation.components.text

import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderClass
import com.squareup.moshi.Json

@RenderClass(type = RenderType.TEXT)
data class TextModel(
    @Json(name = "text") val text: String
)