package com.javi.product.detail.api

import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

interface ProductDetailScreenLoader {

    fun getDestination(
        imageUrl: String
    ): Direction

    fun provideNavGraph(): NavGraphSpec
}