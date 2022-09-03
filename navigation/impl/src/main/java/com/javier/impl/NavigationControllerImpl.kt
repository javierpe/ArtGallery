package com.javier.impl

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.javier.api.NavigationController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Direction
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
class NavigationControllerImpl @Inject constructor() : NavigationController {

    private var navHostController: NavHostController? = null

    @Composable
    override fun init(): NavHostController {
        navHostController = rememberAnimatedNavController()
        return navHostController!!
    }

    override fun navigateTo(direction: Direction) {
        navHostController?.navigate(direction)
    }

    override fun popBackStack() {
        navHostController?.popBackStack()
    }

    override fun hasBackElement(): Boolean {
        return !navHostController?.backQueue.isNullOrEmpty()
    }

    override fun isHome(): Boolean {
        return navHostController?.backQueue?.size == 2
    }
}