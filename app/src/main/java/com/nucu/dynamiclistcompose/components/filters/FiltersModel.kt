package com.nucu.dynamiclistcompose.components.filters

import com.google.gson.annotations.SerializedName

data class FiltersModel(
    @SerializedName("items") val items: List<FilterItemModel>
)

data class FilterItemModel(
    @SerializedName("text") val text: String,
    @SerializedName("go_to") val goTo: String
)