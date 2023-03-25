package com.javi.dynamic.list.presentation.components.profile

import com.javi.render.data.RenderType
import com.javi.render.processor.annotations.render.RenderClass
import com.squareup.moshi.Json

@RenderClass(type = RenderType.PROFILE)
data class ProfileModel(
    @Json(name = "name") val name: String,
    @Json(name = "short_description") val shortDescription: String,
    @Json(name = "life_date") val lifeDate: String,
    @Json(name = "country") val country: String,
    @Json(name = "color") val color: String
)
