package com.nucu.dynamiclistcompose.data.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class DataContentModel(
    @SerializedName("header") val header: List<ComponentModel>,
    @SerializedName("body") val body: List<ComponentModel>
)

data class ComponentModel(
    @SerializedName("render") val render: String,
    @SerializedName("index") val index: Int,
    @SerializedName("resource") val resource: JsonObject? = null
)