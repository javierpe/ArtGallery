package com.javi.cards.page.impl.pageLoader

import com.javi.cards.page.api.CardsPageLoader
import com.javi.cards.page.impl.CardsPageNavGraph
import com.javi.cards.page.impl.destinations.CardsPageDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.Direction
import javax.inject.Inject

class CardsPageLoaderImpl @Inject constructor(): CardsPageLoader {

    override fun getDestination(
        id: Int,
        title: String
    ): Direction {
        return CardsPageDestination(
            id = id,
            title = title
        )
    }

    override fun provideNavGraph() = CardsPageNavGraph

    override fun provideDestinationSpec(): DestinationSpec<*> = CardsPageDestination
}