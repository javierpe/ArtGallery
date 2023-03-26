package com.javi.navigation

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.javi.home.HomeScreenNavGraph
import com.javi.product.detail.presentation.screens.ProductDetailNavGraph
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

private const val ANIMATION_DURATION = 700

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterial3WindowSizeClassApi::class
)
@Composable
fun MainNavigationHost(
    activity: Activity
) {
    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            /**
             * Animation by Route:
             * targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
             */
            /**
             * Animation by Route:
             * targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
             */
            /**
             * Animation by Route:
             * targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
             */
            /**
             * Animation by Route:
             * targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
             */
            /**
             * Animation by Route:
             * targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
             */
            /**
             * Animation by Route:
             * targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
             */
            /**
             * Animation by Route:
             * targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
             */
            /**
             * Animation by Route:
             * targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
             */
            enterTransition = {
                fadeIn(
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

    DestinationsNavHost(
        navGraph = RootNavGraph,
        engine = navHostEngine,
        navController = rememberAnimatedNavController(),
        dependenciesContainerBuilder = {
            dependency(calculateWindowSizeClass(activity).widthSizeClass)
        }
    )
}

object RootNavGraph: NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = HomeScreenNavGraph

    override val nestedNavGraphs = listOf(
        HomeScreenNavGraph,
        ProductDetailNavGraph,
    )
}