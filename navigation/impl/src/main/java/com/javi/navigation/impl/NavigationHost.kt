package com.javi.navigation.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.javi.design.system.data.models.NavigationBarItem
import com.javi.design.system.molecules.NavigationBar
import com.javi.home.HomeNavGraph
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.NavHostEngine

@Suppress("LongParameterList")
@Composable
fun NavigationHost(
    navHostEngine: NavHostEngine,
    navHostController: NavHostController,
    currentDestinationRouteName: String,
    showBottomNavigationBar: Boolean = false,
    graphList: List<NavGraphSpec>,
    navigationBarItems: List<NavigationBarItem>
) {
    Scaffold(
        bottomBar = {
            if (showBottomNavigationBar) {
                NavigationBar(
                    selected = currentDestinationRouteName,
                    navItems = navigationBarItems
                )
            }
        }
    ) { paddingValues ->

        DestinationsNavHost(
            modifier = Modifier.padding(paddingValues),
            navGraph = RootNavGraph(graphList),
            engine = navHostEngine,
            navController = navHostController
        )
    }
}

class RootNavGraph constructor(
    graphList: List<NavGraphSpec>
): NavGraphSpec {

    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = HomeNavGraph

    override val nestedNavGraphs = graphList
}