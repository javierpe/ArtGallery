package com.nucu.dynamiclistcompose.presentation.components.profile

import com.squareup.moshi.Json

data class ProfileModel(
    @Json(name = "name") val name: String,
    @Json(name = "short_description") val shortDescription: String,
    @Json(name = "life_date") val lifeDate: String,
    @Json(name = "country") val country: String,
    @Json(name = "color") val color: String
)
