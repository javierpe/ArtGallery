package com.javi.basket.impl

import com.javi.basket.api.BasketUpdatesUseCase
import com.javi.data.ProductImageModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class BasketUpdatesUseCaseImpl @Inject constructor(
    private val localBasket: LocalBasket
) : BasketUpdatesUseCase {

    override val basketProducts: SharedFlow<List<ProductImageModel>>
        get() = localBasket.basketProducts
}
