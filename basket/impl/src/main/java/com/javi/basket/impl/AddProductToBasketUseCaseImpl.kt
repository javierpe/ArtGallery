package com.javi.basket.impl

import com.javi.basket.api.AddProductToBasketUseCase
import com.javi.data.ProductImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddProductToBasketUseCaseImpl @Inject constructor(
    private val localBasket: LocalBasket
) : AddProductToBasketUseCase {

    override operator fun invoke(productImageModel: ProductImageModel) {
        val alreadyOnBasket = localBasket.currentBasket.firstOrNull {
            it.id == productImageModel.id
        }

        if (alreadyOnBasket != null) {
            // Increment
            val index = localBasket.currentBasket.indexOf(alreadyOnBasket)
            val product = localBasket.currentBasket[index]
            localBasket.currentBasket[index] = product.copy(quantity = product.quantity + 1)
        } else {
            // Add
            localBasket.currentBasket.add(productImageModel.copy(quantity = 1))
        }

        GlobalScope.launch(Dispatchers.Default) {
            localBasket.updateState()
        }
    }
}
