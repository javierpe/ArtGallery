package com.nucu.dynamiclistcompose.models.tooltip

data class ShowCaseStrategy(
    val expirationTime: Int = 3000,
    val onlyUserInteraction: Boolean? = null,
    val firstToHappen: Boolean? = null
)
