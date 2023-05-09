package com.javi.dynamic.list.domain.api.useCases

import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.domain.models.ShowCaseResultModel

interface GetTooltipSequenceUseCase {

    suspend operator fun invoke(
        header: List<ComponentItemModel>,
        body: List<ComponentItemModel>
    ): ShowCaseResultModel
}
