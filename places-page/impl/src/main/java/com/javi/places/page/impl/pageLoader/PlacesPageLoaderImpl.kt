package com.javi.places.page.impl.pageLoader

import com.javi.places.page.api.PlacesPageLoader
import com.javi.places.page.impl.PlacesPageNavGraph
import com.javi.places.page.impl.destinations.PlacesPageDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import javax.inject.Inject

class PlacesPageLoaderImpl @Inject constructor(): PlacesPageLoader {

    override fun getDestination(): Direction {
        return PlacesPageDestination()
    }

    override fun provideNavGraph(): NavGraphSpec {
        return PlacesPageNavGraph
    }

    override fun provideDestinationSpec() = PlacesPageDestination
}