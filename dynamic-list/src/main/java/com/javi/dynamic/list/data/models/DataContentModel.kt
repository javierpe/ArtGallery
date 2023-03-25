package com.javi.dynamic.list.data.models

import com.dynamic.factory.ComponentModel
import com.squareup.moshi.Json

data class DataContentModel(
    @Json(name = "header") val header: List<ComponentModel<Any>>,
    @Json(name = "body") val body: List<ComponentModel<Any>>
)