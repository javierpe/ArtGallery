package com.javi.product.detail.api

import com.ramcosta.composedestinations.spec.Direction

interface ProductDetailScreenLoader {

    fun getDestination(
        imageUrl: String
    ): Direction
}