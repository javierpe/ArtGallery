package com.javi.dynamic.list.presentation.components.banner

import com.javi.data.ProductImageModel
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderModel
import com.squareup.moshi.Json

@RenderModel(type = RenderType.BANNER)
data class BannerModel(
    @Json(name = "product") val product: ProductImageModel,
    @Json(name = "info") val bannerInfo: BannerInfo? = null
)

data class BannerInfo(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String
)

