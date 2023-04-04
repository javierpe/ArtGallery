package com.javi.render.processor.core

enum class RenderType constructor(val value: String) {
    CARDS("cards"),
    BANNER("banner"),
    MESSAGE("message"),
    TEXT("text"),
    FILTERS("filters"),
    BANNER_CAROUSEL("banner_carousel"),
    FACES("faces"),
    PROFILE("profile"),
    POSTER("poster"),
    CARDS_GRID("cards_grid")
}