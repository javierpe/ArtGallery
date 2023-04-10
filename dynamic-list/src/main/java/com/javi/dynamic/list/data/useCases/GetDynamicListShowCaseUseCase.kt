package com.javi.dynamic.list.data.useCases

import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.models.DynamicListContainer

interface GetDynamicListShowCaseUseCase {

    suspend operator fun invoke(
        dynamicListContainer: DynamicListContainer
    ): DynamicListUIState
}
