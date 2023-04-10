package com.javi.cards.page.api

import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec

interface GetCardsPageUseCase {

    val route: String

    val navGraph: NavGraphSpec

    operator fun invoke(
        id: Int = 0,
        title: String = ""
    ): Direction
}