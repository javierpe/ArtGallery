package com.javi.basket.impl

import com.javi.basket.impl.di.IODispatcher
import com.javi.data.ProductImageModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class BasketUpdater @Inject constructor(
    @IODispatcher val coroutineDispatcher: CoroutineDispatcher
) {

    val currentBasket: MutableList<ProductImageModel> = mutableListOf()

    private val _basketProducts = MutableSharedFlow<List<ProductImageModel>>(
        replay = 5,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val basketProducts: SharedFlow<List<ProductImageModel>> = _basketProducts

    init {
        _basketProducts.tryEmit(emptyList())
    }

    internal fun updateState() = runBlocking {
        launch(coroutineDispatcher) {
            _basketProducts.emit(currentBasket.distinct())
        }
    }
}
