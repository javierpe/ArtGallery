package com.javi.basket.impl

import com.javi.basket.api.DecrementProductOnBasketUseCase
import com.javi.data.ProductImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DecrementProductOnBasketUseCaseImpl @Inject constructor(
    private val localBasket: LocalBasket
) : DecrementProductOnBasketUseCase {

    override operator fun invoke(productImageModel: ProductImageModel) {
        val alreadyOnBasket = localBasket.currentBasket.firstOrNull {
            it.id == productImageModel.id
        }

        if (alreadyOnBasket != null && alreadyOnBasket.quantity == 0) {
            // Remove
            localBasket.currentBasket.removeAt(localBasket.currentBasket.indexOf(alreadyOnBasket))
        } else if (alreadyOnBasket != null) {
            // Decrement
            val index = localBasket.currentBasket.indexOf(alreadyOnBasket)
            val product = localBasket.currentBasket[index]
            localBasket.currentBasket[index] = product.copy(quantity = product.quantity - 1)
        }

        GlobalScope.launch(Dispatchers.Default) {
            localBasket.updateState()
        }
    }
}
