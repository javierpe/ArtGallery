package com.javi.dynamic.list.presentation.components.faces

import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderModel
import com.squareup.moshi.Json

@RenderModel(type = RenderType.FACES)
data class FacesModel(
    @Json(name = "items") val items: List<FacesItemModel>
)

data class FacesItemModel(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
    @Json(name = "go_to") val goTo: Int
)
