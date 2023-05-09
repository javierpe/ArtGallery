package com.javi.dynamic.list.domain.api.useCases

import com.javi.dynamic.list.data.models.ComponentItemModel

interface SaveSkeletonsUseCase {

    operator fun invoke(
        body: List<ComponentItemModel>,
        header: List<ComponentItemModel>,
        context: String
    )
}
