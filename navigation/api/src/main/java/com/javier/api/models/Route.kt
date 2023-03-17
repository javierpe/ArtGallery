package com.javier.api.models

sealed class Route(val name: String) {

    object Main : Route("main")

    object BannerScreen : Route("banner_screen") {
        const val IMAGE_URL = "bannerImageURL"
    }

    object CardScreen: Route("card_screen")
}
