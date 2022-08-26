package com.nucu.dynamiclistcompose.presentation.components.banner

import com.google.gson.annotations.SerializedName

data class BannerModel(
    @SerializedName("url") val imageURL: String = "",
    @SerializedName("info") val bannerInfo: BannerInfo? = null
)

data class BannerInfo(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)