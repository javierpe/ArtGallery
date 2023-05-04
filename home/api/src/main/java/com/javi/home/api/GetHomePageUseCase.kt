package com.javi.home.api

import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec

interface GetHomePageUseCase {

    val route: String

    val navGraph: NavGraphSpec

    operator fun invoke(): Direction
}
