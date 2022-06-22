package com.javier.api.models

sealed class Route(val name: String) {
    object Main : Route("main")
    object BannerScreen : Route("banner_screen") {
        const val ARG_INDEX = "bannerIndex"
        const val ARG_TEXT = "bannerText"
    }
}
