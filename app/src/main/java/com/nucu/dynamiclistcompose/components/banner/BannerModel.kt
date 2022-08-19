package com.nucu.dynamiclistcompose.components.banner

import com.google.gson.annotations.SerializedName

data class BannerModel(
    @SerializedName("url") val imageURL: String = ""
)