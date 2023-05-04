package com.javi.basket.api

import com.javi.data.ProductImageModel

interface DecrementProductOnBasketUseCase {

    operator fun invoke(productImageModel: ProductImageModel)
}
