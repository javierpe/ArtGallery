package com.nucu.dynamiclistcompose.data.renders.base

enum class RenderType constructor(val value: String) {
    CARDS("cards"),
    BANNER("banner"),
    MESSAGE("message"),
    TEXT("text"),
    FILTERS("filters"),
    BANNER_CAROUSEL("banner_carousel"),
    FACES("faces"),
    PROFILE("profile"),
    POSTER("poster")
}