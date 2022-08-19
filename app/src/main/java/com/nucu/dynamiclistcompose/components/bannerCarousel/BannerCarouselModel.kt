package com.nucu.dynamiclistcompose.components.bannerCarousel

import com.google.gson.annotations.SerializedName
import com.nucu.dynamiclistcompose.components.banner.BannerModel

data class BannerCarouselModel(
    @SerializedName("banners") val banners: List<BannerModel>
)