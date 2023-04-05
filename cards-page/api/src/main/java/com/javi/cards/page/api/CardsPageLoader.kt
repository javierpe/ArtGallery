package com.javi.cards.page.api

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec

interface CardsPageLoader {

    fun getDestination(
        id: Int = 0,
        title: String = ""
    ): Direction

    fun provideNavGraph(): NavGraphSpec

    fun provideDestinationSpec(): DestinationSpec<*>
}