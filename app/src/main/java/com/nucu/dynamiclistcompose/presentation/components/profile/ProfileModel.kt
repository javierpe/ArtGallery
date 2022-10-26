package com.nucu.dynamiclistcompose.presentation.components.profile

import com.javi.render.processor.RenderClass
import com.javi.render.processor.RenderType
import com.squareup.moshi.Json

@RenderClass(type = RenderType.PROFILE)
data class ProfileModel(
    @Json(name = "name") val name: String,
    @Json(name = "short_description") val shortDescription: String,
    @Json(name = "life_date") val lifeDate: String,
    @Json(name = "country") val country: String,
    @Json(name = "color") val color: String
)
