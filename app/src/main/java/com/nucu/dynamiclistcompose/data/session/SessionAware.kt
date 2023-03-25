package com.nucu.dynamiclistcompose.data.session

import com.javi.data.ProductImageModel

interface SessionAware<T> {

    fun updateProducts(productList: List<ProductImageModel>): T
}