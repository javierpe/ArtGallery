package com.javi.basket.api

import com.javi.data.ProductImageModel
import kotlinx.coroutines.flow.SharedFlow

interface BasketUpdatesUseCase {

    /**
     * Collect current products in basket.
     */
    val basketProducts: SharedFlow<List<ProductImageModel>>
}
