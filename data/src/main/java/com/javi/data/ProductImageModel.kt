package com.javi.data

import com.squareup.moshi.Json

data class ProductImageModel(
    @Json(name = "id") val id: Int,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "image_url") val imageURL: String,
)
