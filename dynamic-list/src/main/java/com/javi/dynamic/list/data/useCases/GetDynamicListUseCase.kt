package com.javi.dynamic.list.data.useCases

import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import kotlinx.coroutines.flow.Flow

interface GetDynamicListUseCase {
    suspend operator fun invoke(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListFlowState>
}
