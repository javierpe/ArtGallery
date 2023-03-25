package com.nucu.dynamiclistcompose.presentation.components.cardsGrid

import com.javi.render.processor.annotations.render.RenderClass
import com.javi.data.ProductImageModel
import com.javi.render.data.RenderType
import com.squareup.moshi.Json

@RenderClass(type = RenderType.CARDS_GRID)
data class CardsGridModel(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: List<ProductImageModel>
)
