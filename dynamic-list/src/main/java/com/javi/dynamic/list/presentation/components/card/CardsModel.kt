package com.javi.dynamic.list.presentation.components.card

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.data.session.SessionAware
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderClass
import com.squareup.moshi.Json

@RenderClass(type = RenderType.CARDS)
data class CardsModel(
    @Json(name = "title") val title: String,
    @Json(name = "items") val cardElements: List<CardElement>
): SessionAware<CardsModel> {
    override fun updateProducts(productList: List<ProductImageModel>): CardsModel {
        val elements = cardElements.map {
            val productsUpdated = it.images.map { product ->
                val p = productList.firstOrNull { it.id == product.id }
                if (p != null) {
                    productList[productList.indexOf(p)]
                } else {
                    product
                }
            }

            it.copy(images = productsUpdated)
        }

        return copy(cardElements = elements)
    }
}

data class CardElement(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: List<ProductImageModel>
)