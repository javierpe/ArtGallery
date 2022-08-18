package com.nucu.dynamiclistcompose.renders.base

enum class RenderType constructor(val value: String) {
    CARDS("cards"),
    BANNER("banner"),
    MESSAGE("message"),
    TEXT("text"),
    FILTERS("filters"),
    BANNER_CAROUSEL("banner_carousel")
}