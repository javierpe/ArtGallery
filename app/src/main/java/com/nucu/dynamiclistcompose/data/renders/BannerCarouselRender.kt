package com.nucu.dynamiclistcompose.data.renders

import com.nucu.dynamiclistcompose.data.renders.base.DynamicListRender
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.components.bannerCarousel.BannerCarouselModel
import javax.inject.Inject

class BannerCarouselRender @Inject constructor(
) : DynamicListRender<BannerCarouselModel> {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.BANNER_CAROUSEL
        )

    override suspend fun <T> resolve(render: String, resource: T?): BannerCarouselModel {
        val model = (resource as BannerCarouselModel)
        return model.copy(banners = model.banners.filter { it.bannerInfo != null })
    }
}