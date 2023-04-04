package com.javi.dynamic.list.presentation.components.banner

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.data.session.SessionAware
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderClass
import com.squareup.moshi.Json

@RenderClass(type = RenderType.BANNER)
data class BannerModel(
    @Json(name = "product") val product: ProductImageModel,
    @Json(name = "info") val bannerInfo: BannerInfo? = null
): SessionAware<BannerModel> {
    override fun updateProducts(productList: List<ProductImageModel>): BannerModel {
        val element = productList.firstOrNull { productInCart -> productInCart.id == product.id }
        return if (element != null) {
            copy(product = element)
        } else {
            this
        }
    }
}

data class BannerInfo(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String
)