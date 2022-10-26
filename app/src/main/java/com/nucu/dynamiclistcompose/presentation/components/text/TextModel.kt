package com.nucu.dynamiclistcompose.presentation.components.text

import com.javi.render.processor.annotations.render.RenderClass
import com.javi.render.processor.data.enums.RenderType
import com.squareup.moshi.Json

@RenderClass(type = RenderType.TEXT)
data class TextModel(
    @Json(name = "text") val text: String
)