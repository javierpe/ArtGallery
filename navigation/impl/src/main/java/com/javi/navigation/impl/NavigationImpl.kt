package com.javi.navigation.impl

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.javi.cards.page.api.CardsPageLoader
import com.javi.design.system.data.models.NavigationBarItem
import com.javi.home.api.HomePageLoader
import com.javi.navigation.api.NavigationApi
import com.javi.places.page.api.PlacesPageLoader
import com.javi.product.detail.api.ProductDetailScreenLoader
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import javax.inject.Inject

private const val ANIMATION_DURATION = 700

class NavigationImpl @Inject constructor(
    private val homePageLoader: HomePageLoader,
    private val productDetailScreenLoader: ProductDetailScreenLoader,
    private val placesPageLoader: PlacesPageLoader,
    private val cardsPageLoader: CardsPageLoader
): NavigationApi {

    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    @Composable
    override fun NavHost() {

        val navHostEngine = rememberAnimatedNavHostEngine(
            navHostContentAlignment = Alignment.TopCenter,
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                enterTransition = {
                    fadeIn(
                        tween(ANIMATION_DURATION)
                    )
                },
                exitTransition = {
                    fadeOut(
                        tween(ANIMATION_DURATION)
                    )
                },
                popEnterTransition = {
                    fadeIn(
                        tween(ANIMATION_DURATION)
                    )
                },
                popExitTransition = {
                    fadeOut(
                        tween(ANIMATION_DURATION)
                    )
                }
            )
        )

        val navHostController = navHostEngine.rememberNavController()

        val currentDestination = navHostController.currentDestinationAsState()

        val navItems = listOf(
            NavigationBarItem(
                name = "Home",
                key = homePageLoader.provideDestinationSpec().route,
                icon = Icons.Rounded.Star
            ) {
                if (currentDestination.value != homePageLoader.provideDestinationSpec()) {
                    navHostController.navigate(
                        direction = homePageLoader.getDestination(),
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
                key = placesPageLoader.provideDestinationSpec().route,
                icon = Icons.Rounded.Place
            ) {
                navHostController.navigate(
                    direction = placesPageLoader.getDestination(),
                    navOptionsBuilder = {
                        launchSingleTop = false
                    }
                )
            }
        )

        val showBottomNavigationBar = currentDestination.value == homePageLoader.provideDestinationSpec() ||
                currentDestination.value == placesPageLoader.provideDestinationSpec()

        NavigationHost(
            navHostEngine = navHostEngine,
            navHostController = navHostController,
            showBottomNavigationBar = showBottomNavigationBar,
            graphList = listOf(
                homePageLoader.provideNavGraph(),
                productDetailScreenLoader.provideNavGraph(),
                placesPageLoader.provideNavGraph(),
                cardsPageLoader.provideNavGraph()
            ),
            navigationBarItems = navItems,
            currentDestinationRouteName = currentDestination.value?.route.orEmpty(),
            startRoute = homePageLoader.provideNavGraph()
        )
    }
}