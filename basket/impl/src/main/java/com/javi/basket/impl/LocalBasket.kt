package com.javi.basket.impl

import com.javi.data.ProductImageModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class LocalBasket @Inject constructor() {

    val currentBasket: MutableList<ProductImageModel> = mutableListOf()

    private val _basketProducts = MutableSharedFlow<List<ProductImageModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    val basketProducts: SharedFlow<List<ProductImageModel>> = _basketProducts

    init {
        _basketProducts.tryEmit(emptyList())
    }

    internal suspend fun updateState() {
        _basketProducts.emit(currentBasket.distinct())
    }
}
