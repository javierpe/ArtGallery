package com.javi.home.api

import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

interface HomePageLoader {

    fun getDestination(): Direction

    fun provideNavGraph(): NavGraphSpec

    fun provideDestinationSpec(): DirectionDestinationSpec
}