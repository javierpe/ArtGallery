package com.javi.basket.impl

import com.javi.basket.api.AddProductToBasketUseCase
import com.javi.data.ProductImageModel
import javax.inject.Inject

class AddProductToBasketUseCaseImpl @Inject constructor(
    private val basketUpdater: BasketUpdater
) : AddProductToBasketUseCase {

    override operator fun invoke(productImageModel: ProductImageModel) {
        val alreadyOnBasket = basketUpdater.currentBasket.firstOrNull {
            it.id == productImageModel.id
        }

        if (alreadyOnBasket != null) {
            // Increment
            val index = basketUpdater.currentBasket.indexOf(alreadyOnBasket)
            val product = basketUpdater.currentBasket[index]
            basketUpdater.currentBasket[index] = product.copy(quantity = product.quantity + 1)
        } else {
            // Add
            basketUpdater.currentBasket.add(productImageModel.copy(quantity = 1))
        }

        basketUpdater.updateState()
    }
}
