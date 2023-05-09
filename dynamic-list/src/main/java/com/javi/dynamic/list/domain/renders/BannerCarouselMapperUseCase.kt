package com.javi.dynamic.list.domain.renders

import com.javi.dynamic.list.domain.renders.base.DynamicListMapperUseCase
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselModel
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@RenderFactory(model = BannerCarouselModel::class)
class BannerCarouselMapperUseCase @Inject constructor() :
    DynamicListMapperUseCase<BannerCarouselModel> {

    override val render = RenderType.BANNER_CAROUSEL

    override suspend fun <T> resolve(render: String, resource: T?): BannerCarouselModel? {
        return withContext(Dispatchers.Default) {
            val model = (resource as BannerCarouselModel)

            val filteredBanners = model.banners.filter { it.bannerInfo != null }

            if (filteredBanners.isEmpty()) {
                null
            } else {
                model.copy(banners = filteredBanners)
            }
        }
    }
}
