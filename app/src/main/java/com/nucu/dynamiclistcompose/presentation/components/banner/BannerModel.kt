package com.nucu.dynamiclistcompose.presentation.components.banner

import com.javi.render.processor.annotations.render.RenderClass
import com.javi.render.processor.data.enums.RenderType
import com.javi.api.data.ProductImageModel
import com.nucu.dynamiclistcompose.data.session.SessionAware
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