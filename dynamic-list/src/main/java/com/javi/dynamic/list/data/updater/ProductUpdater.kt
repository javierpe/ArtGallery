package com.javi.dynamic.list.data.updater

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.dynamic.list.presentation.components.card.CardElement

class ProductUpdater(
    private val updatedProducts: List<ProductImageModel>
) : ProductVisitor {

    override fun visitSingleProduct(product: ProductImageModel): ProductImageModel {
        return updatedProducts.firstOrNull { it.id == product.id } ?: product
    }

    override fun visitProductList(products: List<ProductImageModel>): List<ProductImageModel> {
        val updatedMap = updatedProducts.associateBy { it.id }
        return products.map { product -> updatedMap[product.id] ?: product }
    }

    override fun visitCardsModel(cardElements: List<CardElement>): List<CardElement> {
        return cardElements.map { cardElement ->
            cardElement.copy(
                products = visitProductList(cardElement.products)
            )
        }
    }

    override fun visitBannerCarouselModel(banners: List<BannerModel>): List<BannerModel> {
        return banners.map { bannerElement ->
            bannerElement.copy(
                product = visitSingleProduct(bannerElement.product)
            )
        }
    }
}
