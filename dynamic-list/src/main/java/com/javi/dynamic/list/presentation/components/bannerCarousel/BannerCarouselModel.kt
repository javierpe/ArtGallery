package com.javi.dynamic.list.presentation.components.bannerCarousel

import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderClass
import com.squareup.moshi.Json

@RenderClass(type = RenderType.BANNER_CAROUSEL)
data class BannerCarouselModel(
    @Json(name = "banners") val banners: List<BannerModel>
)
