package com.javi.design.system.data.showCase

data class ShowCaseStrategy(
    val expirationTime: Int = 3000,
    val onlyUserInteraction: Boolean? = null,
    val firstToHappen: Boolean? = null
)
