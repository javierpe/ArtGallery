package com.javi.basket.api

import com.javi.data.ProductImageModel

interface AddProductToBasketUseCase {

    operator fun invoke(productImageModel: ProductImageModel)
}
