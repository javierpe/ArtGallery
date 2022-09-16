package com.nucu.dynamiclistcompose.presentation.components.faces

import com.squareup.moshi.Json

data class FacesModel(
    @Json(name = "items") val items: List<FacesItemModel>
)

data class FacesItemModel(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
    @Json(name = "go_to") val goTo: Int
)
