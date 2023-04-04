package com.javi.dynamic.list.presentation.components.poster

import com.javi.data.ProductImageModel
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderClass
import com.squareup.moshi.Json

@RenderClass(type = RenderType.POSTER)
data class PosterModel(
    @Json(name = "title") val title: String,
    @Json(name = "items") val elements: List<PosterModelItem>
)

data class PosterModelItem(
    @Json(name = "name") val name: String,
    @Json(name = "image") val productImage: ProductImageModel
)
