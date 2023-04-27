package com.javi.dynamic.list.data.useCases

import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.models.ComponentItemModel

interface GetDynamicListShowCaseUseCase {

    suspend operator fun invoke(
        header: List<ComponentItemModel>,
        body: List<ComponentItemModel>
    ): DynamicListUIState
}
