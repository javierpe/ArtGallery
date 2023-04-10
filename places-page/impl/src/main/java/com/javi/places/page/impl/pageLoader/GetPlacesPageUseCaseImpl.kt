package com.javi.places.page.impl.pageLoader

import com.javi.places.page.api.GetPlacesPageUseCase
import com.javi.places.page.impl.PlacesPageNavGraph
import com.javi.places.page.impl.destinations.PlacesPageDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec
import javax.inject.Inject

class GetPlacesPageUseCaseImpl @Inject constructor(): GetPlacesPageUseCase {

    override val navGraph: NavGraphSpec = PlacesPageNavGraph

    override fun invoke(): Direction {
        return PlacesPageDestination()
    }
}