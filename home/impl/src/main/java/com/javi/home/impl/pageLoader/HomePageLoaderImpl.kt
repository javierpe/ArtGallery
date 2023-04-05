package com.javi.home.impl.pageLoader

import com.javi.home.api.HomePageLoader
import com.javi.home.impl.HomeNavGraph
import com.javi.home.impl.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import javax.inject.Inject

class HomePageLoaderImpl @Inject constructor(): HomePageLoader {

    override fun getDestination(): Direction {
        return HomeScreenDestination()
    }

    override fun provideNavGraph() = HomeNavGraph

    override fun provideDestinationSpec() = HomeScreenDestination
}