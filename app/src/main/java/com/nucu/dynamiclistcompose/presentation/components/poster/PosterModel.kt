package com.nucu.dynamiclistcompose.presentation.components.poster

import com.google.gson.annotations.SerializedName

data class PosterModel(
    @SerializedName("title") val title: String,
    @SerializedName("items") val elements: List<PosterModelItem>
)

data class PosterModelItem(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
