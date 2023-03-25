package com.nucu.dynamiclistcompose.presentation.components.bannerCarousel

import com.javi.data.ProductImageModel
import com.javi.render.data.RenderType
import com.javi.render.processor.annotations.render.RenderClass
import com.nucu.dynamiclistcompose.data.session.SessionAware
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.squareup.moshi.Json

@RenderClass(type = RenderType.BANNER_CAROUSEL)
data class BannerCarouselModel(
    @Json(name = "banners") val banners: List<BannerModel>
): SessionAware<BannerCarouselModel> {
    override fun updateProducts(productList: List<ProductImageModel>): BannerCarouselModel {
        val newBanners = banners.map { banner ->
            val productInSession = productList.firstOrNull{ p ->  banner.product.id == p.id}
            if (productInSession != null) {
                banner.copy(product = productInSession)
            } else {
                banner
            }
        }

        return BannerCarouselModel(
            banners = newBanners
        )
    }
}