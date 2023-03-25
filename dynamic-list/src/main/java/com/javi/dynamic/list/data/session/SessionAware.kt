package com.javi.dynamic.list.data.session

import com.javi.data.ProductImageModel

interface SessionAware<T> {

    fun updateProducts(productList: List<ProductImageModel>): T
}