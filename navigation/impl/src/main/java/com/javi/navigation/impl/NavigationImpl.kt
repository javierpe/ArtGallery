package com.javi.navigation.impl

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.javi.cards.page.api.GetCardsPageUseCase
import com.javi.design.system.data.models.NavigationBarItem
import com.javi.home.api.GetHomePageUseCase
import com.javi.navigation.api.NavigationApi
import com.javi.places.page.api.GetPlacesPageUseCase
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import javax.inject.Inject

class NavigationImpl @Inject constructor(
    private val homePageLoader: GetHomePageUseCase,
    private val getProductDetailScreenUseCase: GetProductDetailPageUseCase,
    private val placesPageLoader: GetPlacesPageUseCase,
    private val getCardsPageUseCase: GetCardsPageUseCase
) : NavigationApi {

    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    @Composable
    override fun NavHost() {
        val navHostEngine = rememberAnimatedNavHostEngine(
            navHostContentAlignment = Alignment.TopCenter
        )

        val navHostController = navHostEngine.rememberNavController()

        val currentDestination = navHostController.currentDestinationAsState()

        val navItems = listOf(
            NavigationBarItem(
                name = "Home",
                key = homePageLoader.route,
                icon = Icons.Rounded.Star
            ) {
                if (currentDestination.value?.route != homePageLoader.route) {
                    if (navHostController.isRouteOnBackStack(homePageLoader.navGraph)) {
                        navHostController.popBackStack(
                            route = homePageLoader.route,
                            inclusive = false
                        )
                        return@NavigationBarItem
                    }

                    navHostController.navigate(
                        direction = homePageLoader()
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
                key = placesPageLoader.route,
                icon = Icons.Rounded.Place
            ) {
                if (currentDestination.value?.route != placesPageLoader.route) {
                    if (navHostController.isRouteOnBackStack(placesPageLoader.navGraph)) {
                        navHostController.popBackStack(
                            route = placesPageLoader.route,
                            inclusive = false
                        )
                        return@NavigationBarItem
                    }

                    navHostController.navigate(
                        direction = placesPageLoader()
                    )
                }
            }
        )

        val showBottomNavigationBar = currentDestination.value?.route == homePageLoader.route ||
            currentDestination.value?.route == placesPageLoader.route

        NavigationHost(
            navHostEngine = navHostEngine,
            navHostController = navHostController,
            showBottomNavigationBar = showBottomNavigationBar,
            graphList = listOf(
                homePageLoader.navGraph,
                getProductDetailScreenUseCase.navGraph,
                placesPageLoader.navGraph,
                getCardsPageUseCase.navGraph
            ),
            navigationBarItems = navItems,
            currentDestinationRouteName = currentDestination.value?.route.orEmpty(),
            startRoute = homePageLoader.navGraph
        )
    }
}
