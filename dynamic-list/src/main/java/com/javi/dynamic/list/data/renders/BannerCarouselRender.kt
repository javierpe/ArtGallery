package com.javi.dynamic.list.data.renders

import com.javi.dynamic.list.data.renders.base.DynamicListRender
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselModel
import com.javi.render.data.RenderType
import com.javi.render.processor.annotations.render.RenderFactory
import javax.inject.Inject

@RenderFactory(model = BannerCarouselModel::class)
class BannerCarouselRender @Inject constructor() : DynamicListRender<BannerCarouselModel> {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.BANNER_CAROUSEL
        )

    override suspend fun <T> resolve(render: String, resource: T?): BannerCarouselModel {
        val model = (resource as BannerCarouselModel)
        return model.copy(banners = model.banners.filter { it.bannerInfo != null })
    }
}