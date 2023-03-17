package com.nucu.dynamiclistcompose.presentation.components.prodcut

import com.squareup.moshi.Json

data class ProductImageModel(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "quantity") val quantity: Int = 0,
    @Json(name = "image_url") val imageURL: String = "",
)
