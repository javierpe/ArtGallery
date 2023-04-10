package com.javi.product.detail.api

import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec

interface GetProductDetailPageUseCase {

    val route: String

    val navGraph: NavGraphSpec

    operator fun invoke(
        imageUrl: String
    ): Direction
}
