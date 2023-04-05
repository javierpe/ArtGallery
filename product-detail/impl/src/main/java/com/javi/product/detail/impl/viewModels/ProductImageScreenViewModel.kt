package com.javi.product.detail.impl.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javi.basket.api.LocalBasketApi
import com.javi.data.ProductImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductImageScreenViewModel @Inject constructor(
    private val localBasketApi: LocalBasketApi
): ViewModel() {

    private val _productQuantityUpdates = MutableStateFlow(0)
    val productQuantityUpdates: StateFlow<Int> = _productQuantityUpdates

    fun setProduct(productImageModel: ProductImageModel) {
        viewModelScope.launch {
            localBasketApi.basketProducts.collect { list ->
                list.firstOrNull { it.id == productImageModel.id }?.let {
                    _productQuantityUpdates.value = it.quantity
                }
            }
        }
    }

    fun onAdd(productImageModel: ProductImageModel) {
        viewModelScope.launch {
            localBasketApi.onAdd(productImageModel)
        }
    }

    fun onDecrement(productImageModel: ProductImageModel) {
        viewModelScope.launch {
            localBasketApi.onDecrement(productImageModel)
        }
    }
}