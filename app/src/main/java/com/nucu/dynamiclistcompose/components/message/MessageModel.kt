package com.nucu.dynamiclistcompose.components.message

import com.google.gson.annotations.SerializedName

data class MessageModel(
    @SerializedName("message") val message: String
)