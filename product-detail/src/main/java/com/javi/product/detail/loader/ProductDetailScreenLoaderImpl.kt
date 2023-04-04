package com.javi.product.detail.loader

import com.javi.product.detail.api.ProductDetailScreenLoader
import com.javi.product.detail.presentation.screens.destinations.ProductImageScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import javax.inject.Inject

class ProductDetailScreenLoaderImpl @Inject constructor(

): ProductDetailScreenLoader {

    override fun getDestination(
        imageUrl: String
    ): Direction {
        return ProductImageScreenDestination(
            imageUrl = imageUrl
        )
    }
}