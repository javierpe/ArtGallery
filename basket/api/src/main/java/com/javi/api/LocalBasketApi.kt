package com.javi.api

import com.javi.api.data.ProductImageModel
import kotlinx.coroutines.flow.SharedFlow

interface LocalBasketApi {

    val basketProducts : SharedFlow<List<ProductImageModel>>

    suspend fun addToCart(
        productImageModel: ProductImageModel
    )
}