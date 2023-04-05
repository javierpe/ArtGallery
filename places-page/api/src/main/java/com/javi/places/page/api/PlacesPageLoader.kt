package com.javi.places.page.api

import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

interface PlacesPageLoader {

    fun getDestination(): Direction

    fun provideNavGraph(): NavGraphSpec

    fun provideDestinationSpec(): DirectionDestinationSpec
}