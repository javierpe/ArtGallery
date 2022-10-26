package com.nucu.dynamiclistcompose.presentation.components.bannerCarousel

import com.javi.render.processor.RenderClass
import com.javi.render.processor.RenderType
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.squareup.moshi.Json

@RenderClass(type = RenderType.BANNER_CAROUSEL)
data class BannerCarouselModel(
    @Json(name = "banners") val banners: List<BannerModel>
)