package com.nucu.dynamiclistcompose.presentation.components.message

import com.squareup.moshi.Json

data class MessageModel(
    @Json(name = "message") val message: String
)