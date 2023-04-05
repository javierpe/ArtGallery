package com.javi.product.detail.impl.pageLoader

import com.javi.product.detail.api.ProductDetailScreenLoader
import com.javi.product.detail.impl.presentation.screens.ProductDetailNavGraph
import com.javi.product.detail.impl.presentation.screens.destinations.ProductImageScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import javax.inject.Inject

class ProductDetailScreenLoaderImpl @Inject constructor(): ProductDetailScreenLoader {

    override fun getDestination(
        imageUrl: String
    ): Direction {
        return ProductImageScreenDestination(
            imageUrl = imageUrl
        )
    }

    override fun provideNavGraph() = ProductDetailNavGraph
}