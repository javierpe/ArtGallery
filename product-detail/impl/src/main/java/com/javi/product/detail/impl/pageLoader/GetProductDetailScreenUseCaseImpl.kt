package com.javi.product.detail.impl.pageLoader

import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.product.detail.impl.presentation.screens.ProductDetailNavGraph
import com.javi.product.detail.impl.presentation.screens.destinations.ProductImageScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec
import javax.inject.Inject

class GetProductDetailScreenUseCaseImpl @Inject constructor() : GetProductDetailPageUseCase {

    override val route: String = ProductImageScreenDestination.route

    override val navGraph: NavGraphSpec = ProductDetailNavGraph

    override fun invoke(imageURL: String): Direction {
        return ProductImageScreenDestination(
            imageURL = imageURL
        )
    }
}
