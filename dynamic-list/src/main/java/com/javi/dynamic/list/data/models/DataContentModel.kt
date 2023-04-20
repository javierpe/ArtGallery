package com.javi.dynamic.list.data.models

import com.dynamic.factory.ComponentModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class DataContentModel(
    @Json(name = "header") val header: List<ComponentModel<Any>>,
    @Json(name = "body") val body: List<ComponentModel<Any>>
)
