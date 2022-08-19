package com.nucu.dynamiclistcompose.components.faces

import com.google.gson.annotations.SerializedName

data class FacesModel(
    @SerializedName("items") val items: List<FacesItemModel>
)

data class FacesItemModel(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
