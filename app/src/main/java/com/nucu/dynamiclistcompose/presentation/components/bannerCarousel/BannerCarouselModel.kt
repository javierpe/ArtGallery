package com.nucu.dynamiclistcompose.presentation.components.bannerCarousel

import com.google.gson.annotations.SerializedName
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel

data class BannerCarouselModel(
    @SerializedName("banners") val banners: List<BannerModel>
)