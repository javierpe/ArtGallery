package com.javi.dynamic.list.data.extensions

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.session.SessionAware

fun DynamicListUIState.SuccessAction.propagateBasketProducts(
    basketProducts: List<ProductImageModel>
): DynamicListUIState.SuccessAction {
    val updatedBody = container.body.map { componentItemModel ->
        componentItemModel.updateSession(basketProducts)
    }

    val updateDynamicListElementBody = body.map { dynamicListElement ->
        dynamicListElement.copy(
            componentItemModel = dynamicListElement.componentItemModel.updateSession(basketProducts)
        )
    }

    return DynamicListUIState.SuccessAction(
        container = container.copy(body = updatedBody),
        body = updateDynamicListElementBody,
        header = header,
        showCaseQueue = showCaseQueue
    )
}

fun ComponentItemModel.updateSession(basketProducts: List<ProductImageModel>): ComponentItemModel {
    return if (resource is SessionAware<*>) {
        copy(resource = resource.updateProducts(basketProducts)!!)
    } else {
        this
    }
}