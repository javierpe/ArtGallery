package com.javi.dynamic.list.data.updater

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.dynamic.list.presentation.components.card.CardElement

interface ProductVisitor {

    fun visitSingleProduct(product: ProductImageModel): ProductImageModel

    fun visitProductList(products: List<ProductImageModel>): List<ProductImageModel>

    fun visitCardsModel(cardElements: List<CardElement>): List<CardElement>

    fun visitBannerCarouselModel(banners: List<BannerModel>): List<BannerModel>
}
