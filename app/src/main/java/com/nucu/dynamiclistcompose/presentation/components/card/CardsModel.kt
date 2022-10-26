package com.nucu.dynamiclistcompose.presentation.components.card

import com.javi.render.processor.annotations.render.RenderClass
import com.javi.render.processor.data.enums.RenderType
import com.squareup.moshi.Json

@RenderClass(type = RenderType.CARDS)
data class CardsModel(
    @Json(name = "title") val title: String,
    @Json(name = "items") val cardElements: List<CardElement>
)

data class CardElement(
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: List<CardImage>
)

data class CardImage(
    @Json(name = "image_url") val imageURL: String
)