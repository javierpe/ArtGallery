package com.nucu.dynamiclistcompose.presentation.components.profile

import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("name") val name: String,
    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("life_date") val lifeDate: String,
    @SerializedName("country") val country: String,
    @SerializedName("color") val color: String
)
