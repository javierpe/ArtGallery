package com.javi.navigation.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.javi.card.page.CardsPageNavGraph
import com.javi.design.system.data.models.NavigationBarItem
import com.javi.design.system.molecules.NavigationBar
import com.javi.home.HomeNavGraph
import com.javi.home.destinations.HomeScreenDestination
import com.javi.places.page.PlacesPageNavGraph
import com.javi.places.page.destinations.PlacesPageDestination
import com.javi.product.detail.presentation.screens.ProductDetailNavGraph
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.NavHostEngine
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun NavigationHost(
    navHostEngine: NavHostEngine,
    navHostController: NavHostController
) {

    val currentDestination = navHostController.currentDestinationAsState()

    Scaffold(
        bottomBar = {
            if (currentDestination.value?.isWhiteListed() == true) {
                NavigationBar(
                    selected = currentDestination.value?.route.orEmpty(),
                    navItems = listOf(
                        NavigationBarItem(
                            name = "Home",
                            key = HomeScreenDestination.route,
                            icon = Icons.Rounded.Star
                        ) {
                            if (currentDestination.value != HomeScreenDestination) {
                                navHostController.navigate(
                                    direction = HomeScreenDestination(),
                                    navOptionsBuilder = {
                                        launchSingleTop = false
                                        restoreState = true
                                    }
                                )
                            }
                        },
                        NavigationBarItem(
                            name = "Favorites",
                            key = "favs",
                            icon = Icons.Rounded.Favorite
                        ) {

                        },
                        NavigationBarItem(
                            name = "Places",
                            key = PlacesPageDestination.route,
                            icon = Icons.Rounded.Place
                        ) {
                            navHostController.navigate(
                                direction = PlacesPageDestination(),
                                navOptionsBuilder = {
                                    launchSingleTop = false
                                }
                            )
                        }
                    )
                )
            }
        }
    ) { paddingValues ->
        DestinationsNavHost(
            modifier = Modifier.padding(paddingValues),
            navGraph = RootNavGraph,
            engine = navHostEngine,
            navController = navHostController
        )
    }
}

object RootNavGraph: NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = HomeNavGraph

    override val nestedNavGraphs = listOf(
        HomeNavGraph,
        ProductDetailNavGraph,
        CardsPageNavGraph,
        PlacesPageNavGraph
    )
}

fun DestinationSpec<*>.isWhiteListed(): Boolean {
    return this == HomeScreenDestination || this == PlacesPageDestination
}