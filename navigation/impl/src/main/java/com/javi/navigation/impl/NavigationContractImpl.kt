package com.javi.navigation.impl

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.javi.navigation.api.NavigationContractApi
import com.javi.navigation.api.NavigationDestinationsApi
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import javax.inject.Inject

class NavigationContractImpl @Inject constructor(
    private val navigationDestinationsApi: NavigationDestinationsApi
): NavigationContractApi {

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

        val currentLifeCycleOwner = LocalLifecycleOwner.current

        DisposableEffect(currentLifeCycleOwner) {

            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_CREATE) {
                    navigationDestinationsApi.setUp(navHostController)
                }
            }

            currentLifeCycleOwner.lifecycle.addObserver(observer)

            onDispose {
                navigationDestinationsApi.onDispose()
                currentLifeCycleOwner.lifecycle.removeObserver(observer)
            }
        }

        NavigationHost(
            navHostEngine = navHostEngine,
            navHostController = navHostController
        )
    }
}