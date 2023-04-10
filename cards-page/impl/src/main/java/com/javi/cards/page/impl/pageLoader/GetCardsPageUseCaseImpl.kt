package com.javi.cards.page.impl.pageLoader

import com.javi.cards.page.api.GetCardsPageUseCase
import com.javi.cards.page.impl.CardsPageNavGraph
import com.javi.cards.page.impl.destinations.CardsPageDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec
import javax.inject.Inject

class GetCardsPageUseCaseImpl @Inject constructor(): GetCardsPageUseCase {

    override val route: String = CardsPageNavGraph.route

    override val navGraph: NavGraphSpec = CardsPageNavGraph

    override operator fun invoke(
        id: Int,
        title: String
    ): Direction {
        return CardsPageDestination(
            id = id,
            title = title
        )
    }
}