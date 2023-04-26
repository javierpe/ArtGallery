package com.javi.dynamic.list.data.extensions

import com.javi.data.ProductImageModel
import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.updater.ProductUpdater
import com.javi.dynamic.list.data.updater.ProductVisitor
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselModel
import com.javi.dynamic.list.presentation.components.card.CardsModel

internal fun Any.accept(visitor: ProductVisitor): Any {
    return when (this) {
        is BannerModel -> copy(product = visitor.visitSingleProduct(this.product))
        is CardsModel -> copy(cardElements = visitor.visitCardsModel(this.cardElements))
        is BannerCarouselModel -> copy(banners = visitor.visitBannerCarouselModel(this.banners))
        else -> this
    }
}

fun DynamicListUIState.SuccessAction.updateProducts(
    basketProducts: List<ProductImageModel>
): DynamicListUIState.SuccessAction {
    val productUpdater = ProductUpdater(
        basketProducts
    )

    val updatedBody = container.body.flatMap { element ->
        listOf(element.copy(resource = element.resource.accept(productUpdater)))
    }

    val updateDynamicListElementBody = body.flatMap { element ->
        listOf(
            updatedBody.firstOrNull { component ->
                component.index == element.componentItemModel.index
            }?.let {
                element.copy(componentItemModel = it)
            } ?: element
        )
    }

    return DynamicListUIState.SuccessAction(
        container = container.copy(body = updatedBody),
        body = updateDynamicListElementBody,
        header = header,
        showCaseQueue = showCaseQueue
    )
}
