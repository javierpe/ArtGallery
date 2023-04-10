package com.javi.places.page.api

import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec

interface GetPlacesPageUseCase {

    val navGraph: NavGraphSpec

    operator fun invoke(): Direction
}