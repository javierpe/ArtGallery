package com.javi.dynamic.list.data.updater.visitors

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.updater.base.ComponentItemModelVisitor
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselModel
import com.javi.dynamic.list.presentation.components.card.CardElement
import com.javi.dynamic.list.presentation.components.card.CardsModel

class ProductVisitor(
    private val updatedProducts: List<ProductImageModel>,
    private val currentBody: List<ComponentItemModel>
) : ComponentItemModelVisitor(currentBody) {

    private fun visitSingleProduct(product: ProductImageModel): ProductImageModel {
        return updatedProducts.firstOrNull { it.id == product.id } ?: product
    }

    private fun visitProductList(products: List<ProductImageModel>): List<ProductImageModel> {
        val updatedMap = updatedProducts.associateBy { it.id }
        return products.map { product -> updatedMap[product.id] ?: product }
    }

    private fun visitCardsModel(cardElements: List<CardElement>): List<CardElement> {
        return cardElements.map { cardElement ->
            cardElement.copy(
                products = visitProductList(cardElement.products)
            )
        }
    }

    private fun visitBannerCarouselModel(banners: List<BannerModel>): List<BannerModel> {
        return banners.map { bannerElement ->
            bannerElement.copy(
                product = visitSingleProduct(bannerElement.product)
            )
        }
    }

    override suspend fun update(): List<ComponentItemModel> {
        val updated = currentBody.flatMap { dataModel ->
            val resource = dataModel.resource
            listOf(
                ComponentItemModel(
                    index = dataModel.index,
                    render = dataModel.render,
                    resource = when (resource) {
                        is BannerModel -> resource.copy(product = visitSingleProduct(resource.product))
                        is CardsModel -> resource.copy(cardElements = visitCardsModel(resource.cardElements))
                        is BannerCarouselModel -> resource.copy(banners = visitBannerCarouselModel(resource.banners))
                        else -> resource
                    }
                )
            )
        }

        return propagate(updated)
    }
}
