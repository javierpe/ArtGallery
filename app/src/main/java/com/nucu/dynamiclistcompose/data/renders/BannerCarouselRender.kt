package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nucu.dynamiclistcompose.presentation.components.bannerCarousel.BannerCarouselModel
import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import javax.inject.Inject

class BannerCarouselRender @Inject constructor(
    private val gson: Gson
) : DynamicListRender<BannerCarouselModel> {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.BANNER_CAROUSEL
        )

    override suspend fun resolve(render: String, resource: JsonObject?): BannerCarouselModel {
        return gson.fromJson(resource, BannerCarouselModel::class.java)
    }
}