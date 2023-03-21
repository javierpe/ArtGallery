package com.javi.api

import com.javi.api.data.ProductImageModel
import kotlinx.coroutines.flow.SharedFlow

interface LocalBasketApi {

    /**
     * Collect current products in basket.
     */
    val basketProducts : SharedFlow<List<ProductImageModel>>

    /**
     * Call this before consume basket products.
     */
    suspend fun setUp()

    /**
     * Add product to local basket.
     */
    suspend fun addToCart(
        productImageModel: ProductImageModel
    )
}