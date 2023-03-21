package com.nucu.dynamiclistcompose.data.session

import com.javi.api.data.ProductImageModel

interface SessionAware<T> {

    fun updateProducts(productList: List<ProductImageModel>): T
}