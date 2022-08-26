package com.javier.impl

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.javier.api.NavigationController
import com.javier.api.models.Route
import javax.inject.Inject

class NavigationControllerImpl @Inject constructor() : NavigationController {

    private lateinit var navHostController: NavHostController

    @Composable
    override fun NavHost(builder: NavGraphBuilder.(NavHostController) -> Unit) {
        navHostController = rememberNavController()
        NavHost(
            navController = navHostController,
            startDestination = Route.Main.name,
            builder = { builder.invoke(this, navHostController) }
        )
    }

    override fun navigateTo(
        route: Route,
        args: List<String>?,
        builder: (NavOptionsBuilder.() -> Unit)?
    ) {
        navigateTo(route.name, args, builder)
    }

    override fun navigateTo(
        route: String,
        args: List<String>?,
        builder: (NavOptionsBuilder.() -> Unit)?
    ) {
        navHostController.navigate(route = route + args?.joinToString(prefix = "/", separator = "/").orEmpty()) {
            builder?.invoke(this)
        }
    }

    override fun popBackStack() {
        navHostController.popBackStack()
    }

    override fun hasBackElement(): Boolean {
        return navHostController.backQueue.isNotEmpty()
    }

    override fun isHome(): Boolean {
        return navHostController.backQueue.size == 2
    }
}