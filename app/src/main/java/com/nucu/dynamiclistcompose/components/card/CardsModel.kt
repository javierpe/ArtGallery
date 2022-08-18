package com.nucu.dynamiclistcompose.components.card

import com.google.gson.annotations.SerializedName

data class CardsModel(
    @SerializedName("items") val cardElements: List<CardElement>
)

data class CardElement(
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: List<CardImage>
)

data class CardImage(
    @SerializedName("image_url") val imageURL: String
)