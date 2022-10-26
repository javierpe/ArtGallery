package com.nucu.dynamiclistcompose.presentation.components.banner

import com.javi.render.processor.RenderClass
import com.javi.render.processor.RenderType
import com.squareup.moshi.Json

@RenderClass(type = RenderType.BANNER)
data class BannerModel(
    @Json(name = "url") val imageURL: String = "",
    @Json(name = "info") val bannerInfo: BannerInfo? = null
)

data class BannerInfo(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String
)