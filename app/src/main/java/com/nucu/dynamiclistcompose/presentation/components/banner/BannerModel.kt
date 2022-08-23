package com.nucu.dynamiclistcompose.presentation.components.banner

import com.google.gson.annotations.SerializedName

data class BannerModel(
    @SerializedName("url") val imageURL: String = ""
)