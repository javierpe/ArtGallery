package com.javi.dynamic.list.presentation.components.cardsGrid

import com.javi.data.ProductImageModel
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderModel
import com.squareup.moshi.Json

@RenderModel(type = RenderType.CARDS_GRID)
data class CardsGridModel(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: List<ProductImageModel>
)
