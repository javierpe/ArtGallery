package com.nucu.dynamiclistcompose.presentation.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.javier.api.models.Route
import com.nucu.dynamiclistcompose.MainScreen
import com.nucu.dynamiclistcompose.bannerScreen.presentation.screens.BannerScreen
import com.nucu.dynamiclistcompose.cardScreen.presentation.screens.CardScreen

/**
 * Navigate to MainScreen
 */
fun NavGraphBuilder.homeNav(widthSizeClass: WindowWidthSizeClass) {
    composable(Route.Main.name) {
        MainScreen(widthSizeClass)
    }
}

/**
 * Navigation to BannerScreen
 */
fun NavGraphBuilder.bannerScreenNav() {
    composable(
        route = Route.BannerScreen.name + "/{${Route.BannerScreen.IMAGE_URL}}",
        arguments = listOf(
            navArgument(Route.BannerScreen.IMAGE_URL) { type = NavType.StringType }
        )
    ) {
        BannerScreen(
            it.arguments?.getString(Route.BannerScreen.IMAGE_URL).orEmpty(),
        )
    }
}

fun NavGraphBuilder.cardScreenNav() {
    composable(
        route = Route.CardScreen.name + "/{${Route.CardScreen.CARD_TEXT}}/{${Route.CardScreen.IMAGE_URL}}",
        arguments = listOf(
            navArgument(Route.CardScreen.CARD_TEXT) { type = NavType.StringType },
            navArgument(Route.CardScreen.IMAGE_URL) { type = NavType.StringType }
        )
    ) {
        val cardText = it.arguments?.getString(Route.CardScreen.CARD_TEXT).orEmpty()
        val data = Gson()
            .fromJson(
                it.arguments?.getString(Route.CardScreen.IMAGE_URL).orEmpty(),
                List::class.java
            ).map { url -> url.toString() }

        CardScreen(
            title = cardText,
            images = data
        )
    }
}