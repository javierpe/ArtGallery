package com.nucu.dynamiclistcompose.data.models

import com.render.factory.ComponentModel
import com.squareup.moshi.Json

data class DataContentModel(
    @Json(name = "header") val header: List<ComponentModel<Any>>,
    @Json(name = "body") val body: List<ComponentModel<Any>>
)