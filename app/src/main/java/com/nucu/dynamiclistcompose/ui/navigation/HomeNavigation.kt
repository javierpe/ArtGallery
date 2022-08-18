package com.nucu.dynamiclistcompose.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.javier.api.models.Route
import com.nucu.dynamiclistcompose.MainScreen
import com.nucu.dynamiclistcompose.ui.examples.screens.BannerScreen

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