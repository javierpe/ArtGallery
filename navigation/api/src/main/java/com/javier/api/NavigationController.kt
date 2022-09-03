package com.javier.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.spec.Direction

interface NavigationController {

    @Composable
    fun init(): NavHostController

    fun navigateTo(direction: Direction)

    fun popBackStack()

    fun hasBackElement(): Boolean

    fun isHome(): Boolean
}