package com.javi.dynamic.list.data.extensions

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.data.actions.DynamicListUIEvents
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.session.SessionAware

fun DynamicListUIEvents.SuccessAction.propagateBasketProducts(
    basketProducts: List<ProductImageModel>
): DynamicListUIEvents.SuccessAction {
    val updatedBody = container.body.map { componentItemModel ->
        componentItemModel.updateSession(basketProducts)
    }

    val updateDynamicListElementBody = body.map { dynamicListElement ->
        dynamicListElement.copy(
            componentItemModel = dynamicListElement.componentItemModel.updateSession(basketProducts)
        )
    }

    return DynamicListUIEvents.SuccessAction(
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