package com.nucu.dynamiclistcompose.presentation.components.poster

import com.javi.render.processor.annotations.render.RenderClass
import com.javi.render.processor.data.enums.RenderType
import com.javi.api.data.ProductImageModel
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
