package com.nucu.dynamiclistcompose.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
fun NavGraphBuilder.HomeNav(widthSizeClass: WindowWidthSizeClass) {
    composable(Route.Main.name) {
        MainScreen(widthSizeClass)
    }
}

/**
 * Navigation to BannerScreen
 */
fun NavGraphBuilder.BannerScreenNav() {
    composable(
        route = Route.BannerScreen.name + "/{${Route.BannerScreen.ARG_INDEX}}/{${Route.BannerScreen.ARG_TEXT}}",
        arguments = listOf(
            navArgument(Route.BannerScreen.ARG_INDEX) { type = NavType.StringType },
            navArgument(Route.BannerScreen.ARG_TEXT) { type = NavType.StringType }
        )
    ) {
        BannerScreen(
            it.arguments?.getString(Route.BannerScreen.ARG_TEXT).orEmpty(),
            it.arguments?.getString(Route.BannerScreen.ARG_INDEX).orEmpty()
        )
    }
}