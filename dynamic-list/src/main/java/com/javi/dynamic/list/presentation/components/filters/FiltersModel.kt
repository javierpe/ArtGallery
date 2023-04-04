package com.javi.dynamic.list.presentation.components.filters

import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderClass
import com.squareup.moshi.Json

@RenderClass(type = RenderType.FILTERS)
data class Filters(
    @Json(name = "items") val items: List<FilterItemModel>
)

data class FilterItemModel(
    @Json(name = "text") val text: String,
    @Json(name = "go_to") val goTo: String,
    @Json(name = "color") val color: String
)