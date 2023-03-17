package com.nucu.dynamiclistcompose.presentation.components.cardsGrid

import com.javi.render.processor.annotations.render.RenderClass
import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.presentation.components.card.CardImage
import com.squareup.moshi.Json

@RenderClass(type = RenderType.CARDS_GRID)
data class CardsGridModel(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: List<CardImage>
)
