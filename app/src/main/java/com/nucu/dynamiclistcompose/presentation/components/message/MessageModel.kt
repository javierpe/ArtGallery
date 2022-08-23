package com.nucu.dynamiclistcompose.presentation.components.message

import com.google.gson.annotations.SerializedName

data class MessageModel(
    @SerializedName("message") val message: String
)