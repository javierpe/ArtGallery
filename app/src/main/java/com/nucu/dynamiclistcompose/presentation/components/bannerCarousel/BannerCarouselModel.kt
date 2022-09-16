package com.nucu.dynamiclistcompose.presentation.components.bannerCarousel

import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.squareup.moshi.Json

data class BannerCarouselModel(
    @Json(name = "banners") val banners: List<BannerModel>
)