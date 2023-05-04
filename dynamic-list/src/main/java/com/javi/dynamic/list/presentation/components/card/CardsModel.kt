package com.javi.dynamic.list.presentation.components.card

import com.javi.data.ProductImageModel
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderModel
import com.squareup.moshi.Json

@RenderModel(type = RenderType.CARDS)
data class CardsModel(
    @Json(name = "title") val title: String,
    @Json(name = "items") val cardElements: List<CardElement>
)

data class CardElement(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "images") val products: List<ProductImageModel>
)
