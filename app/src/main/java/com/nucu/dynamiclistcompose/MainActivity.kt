package com.nucu.dynamiclistcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.metrics.performance.JankStats
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.home.HomeScreenNavGraph
import com.javi.product.detail.presentation.screens.ProductDetailNavGraph
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import dagger.hilt.android.AndroidEntryPoint

private const val ANIMATION_DURATION = 700

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var jankStats: JankStats? = null

    private val jankFrameListener = JankStats.OnFrameListener { frameData ->
        if (frameData.isJank) {
            Log.v("JankStatsDynamicListCompose", frameData.toString())
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalComposeUiApi::class,
        ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DynamicListComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics {
                            testTagsAsResourceId = true
                        },
                    color = colorResource(id = R.color.ic_launcher_background)
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
                            dependency(calculateWindowSizeClass(this@MainActivity).widthSizeClass)
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (jankStats == null) {
            jankStats = JankStats.createAndTrack(window, jankFrameListener)
        } else {
            jankStats?.isTrackingEnabled = true
        }
    }

    override fun onPause() {
        super.onPause()
        jankStats?.isTrackingEnabled = false
    }
}

object RootNavGraph: NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = HomeScreenNavGraph

    override val nestedNavGraphs = listOf(
        HomeScreenNavGraph,
        ProductDetailNavGraph
    )
}
