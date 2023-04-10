package com.javi.dynamic.list.data.useCases

import com.javi.dynamic.list.data.actions.DynamicListUIState

interface GetSkeletonsByContextUseCase {

    suspend operator fun invoke(
        source: String
    ): DynamicListUIState
}