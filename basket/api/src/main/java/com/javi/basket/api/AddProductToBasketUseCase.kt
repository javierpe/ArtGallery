package com.javi.basket.api

import com.javi.data.ProductImageModel

interface AddProductToBasketUseCase {

    suspend operator fun invoke(productImageModel: ProductImageModel)
}
