package com.javi.dynamic.list.domain.api.useCases

import com.javi.dynamic.list.data.actions.DynamicListFlowState
import kotlinx.coroutines.flow.Flow

interface GetBasketTransformUseCase {

    fun combineWithBasket(flow: Flow<DynamicListFlowState>): Flow<DynamicListFlowState>
}
