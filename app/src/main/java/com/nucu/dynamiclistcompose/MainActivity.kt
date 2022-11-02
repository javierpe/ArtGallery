package com.nucu.dynamiclistcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.metrics.performance.JankStats
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.javier.api.NavigationController
import com.javier.api.models.Route
import com.nucu.dynamiclistcompose.presentation.ui.base.ContextView
import com.nucu.dynamiclistcompose.presentation.ui.theme.DynamicListComposeTheme
import com.nucu.dynamiclistcompose.presentation.viewModels.MainViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ANIMATION_DURATION = 700

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationController: NavigationController

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
                    color = MaterialTheme.colors.background
                ) {
                    val navHostEngine = rememberAnimatedNavHostEngine(
                        navHostContentAlignment = Alignment.TopCenter,
                        rootDefaultAnimations = RootNavGraphDefaultAnimations(
                            enterTransition = {
                                if (
                                    targetState.destination.route.orEmpty().contains(Route.CardScreen.name)
                                ) {
                                    slideIntoContainer(
                                        towards = AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(ANIMATION_DURATION)
                                    )
                                } else {
                                    fadeIn(
                                        tween(ANIMATION_DURATION)
                                    )
                                }
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
                        navGraph = NavGraphs.root,
                        engine = navHostEngine,
                        navController = navigationController.init(),
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

@Destination(
    route = "main",
    start = true
)
@RootNavGraph(start = true)
@Composable
fun MainScreen(
    widthSizeClass: WindowWidthSizeClass,
    viewModel: MainViewModel = hiltViewModel()
) {
    ContextView(
        title = "Art Gallery",
        widthSizeClass = widthSizeClass,
        viewModel = viewModel
    )
}
