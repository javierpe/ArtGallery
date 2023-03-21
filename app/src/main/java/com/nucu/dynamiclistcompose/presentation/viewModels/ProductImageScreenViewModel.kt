package com.nucu.dynamiclistcompose.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javi.api.LocalBasketApi
import com.javi.api.data.ProductImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductImageScreenViewModel @Inject constructor(
    private val localBasketApi: LocalBasketApi
): ViewModel() {

    fun addToCart(productImageModel: ProductImageModel) {
        viewModelScope.launch {
            localBasketApi.addToCart(productImageModel)
        }
    }
}