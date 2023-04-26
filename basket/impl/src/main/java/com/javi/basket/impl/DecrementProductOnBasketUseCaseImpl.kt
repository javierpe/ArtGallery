package com.javi.basket.impl

import com.javi.basket.api.DecrementProductOnBasketUseCase
import com.javi.data.ProductImageModel
import javax.inject.Inject

class DecrementProductOnBasketUseCaseImpl @Inject constructor(
    private val basketUpdater: BasketUpdater
) : DecrementProductOnBasketUseCase {

    override operator fun invoke(productImageModel: ProductImageModel) {
        val alreadyOnBasket = basketUpdater.currentBasket.firstOrNull {
            it.id == productImageModel.id
        }

        if (alreadyOnBasket != null && alreadyOnBasket.quantity == 0) {
            // Remove
            basketUpdater.currentBasket.removeAt(basketUpdater.currentBasket.indexOf(alreadyOnBasket))
        } else if (alreadyOnBasket != null) {
            // Decrement
            val index = basketUpdater.currentBasket.indexOf(alreadyOnBasket)
            val product = basketUpdater.currentBasket[index]
            basketUpdater.currentBasket[index] = product.copy(quantity = product.quantity - 1)
        }

        basketUpdater.updateState()
    }
}
