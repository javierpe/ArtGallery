package com.nucu.dynamiclistcompose.models.tooltip

data class TooltipShowStrategy(
    val expirationTime: Int = 3000,
    val showUntilUserInteraction: Boolean? = null,
    val firstToHappen: Boolean? = null
)
