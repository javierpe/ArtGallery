package com.nucu.dynamiclistcompose.presentation.components.text

import com.squareup.moshi.Json

data class TextModel(
    @Json(name = "text") val text: String
)