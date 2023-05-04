package com.javi.dynamic.list.data.useCases

import com.javi.render.processor.core.RenderType

interface GetSkeletonsByContextUseCase {

    suspend operator fun invoke(
        context: String
    ): List<RenderType>
}
