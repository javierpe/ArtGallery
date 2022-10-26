package com.nucu.dynamiclistcompose.presentation.components.text

import com.javi.render.processor.RenderClass
import com.javi.render.processor.RenderType
import com.squareup.moshi.Json

@RenderClass(type = RenderType.TEXT)
data class TextModel(
    @Json(name = "text") val text: String
)