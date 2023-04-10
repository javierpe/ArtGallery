package com.javi.home.impl.pageLoader

import com.javi.home.api.GetHomePageUseCase
import com.javi.home.impl.HomeNavGraph
import com.javi.home.impl.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec
import javax.inject.Inject

class GetHomePageUseCaseImpl @Inject constructor() : GetHomePageUseCase {

    override val route: String = HomeNavGraph.route

    override val navGraph: NavGraphSpec = HomeNavGraph

    override operator fun invoke(): Direction {
        return HomeScreenDestination()
    }
}
