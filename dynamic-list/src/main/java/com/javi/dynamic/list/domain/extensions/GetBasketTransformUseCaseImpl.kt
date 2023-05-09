package com.javi.dynamic.list.domain.extensions

import com.javi.basket.api.BasketUpdatesUseCase
import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.updater.visitors.ProductVisitor
import com.javi.dynamic.list.domain.api.useCases.GetBasketTransformUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

open class GetBasketTransformUseCaseImpl @Inject constructor(
    private val basketUpdatesUseCase: BasketUpdatesUseCase
) : GetBasketTransformUseCase {

    override fun combineWithBasket(flow: Flow<DynamicListFlowState>): Flow<DynamicListFlowState> {
        return flow.combine(basketUpdatesUseCase.basketProducts) { data, basket ->
            if (basket.isNotEmpty() && data is DynamicListFlowState.ResponseAction) {
                val updatedBody = ProductVisitor(
                    updatedProducts = basket,
                    currentBody = data.dataContentModel.body
                ).update()

                DynamicListFlowState.ResponseAction(
                    dataContentModel = data.dataContentModel.copy(body = updatedBody)
                )
            } else {
                data
            }
        }
    }
}
