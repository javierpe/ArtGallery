package com.javi.impl

import com.javi.api.LocalBasketApi
import com.javi.api.data.ProductImageModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalBasketImpl @Inject constructor(

): LocalBasketApi {

    private val localBasketProducts: MutableList<ProductImageModel> = mutableListOf()

    private val _basketProducts = MutableSharedFlow<List<ProductImageModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    override val basketProducts: SharedFlow<List<ProductImageModel>> = _basketProducts

    override suspend fun setUp() {
        _basketProducts.emit(emptyList())
    }

    override suspend fun addToCart(productImageModel: ProductImageModel) {

        val alreadyOnBasket = localBasketProducts.firstOrNull {
            it.id == productImageModel.id
        }

        if (alreadyOnBasket != null) {
            // Increment
            val index = localBasketProducts.indexOf(alreadyOnBasket)
            val product = localBasketProducts[index]
            localBasketProducts[index] = product.copy(quantity = product.quantity + 1)
        } else {
            // Add
            localBasketProducts.add(productImageModel.copy(quantity = 1))
        }

        _basketProducts.emit(localBasketProducts)
    }
}