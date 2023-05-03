package com.javi.dynamic.list.domain.useCases

import com.javi.dynamic.list.data.useCases.GetSkeletonsByContextUseCase
import com.javi.dynamic.list.domain.database.AppDatabase
import com.javi.render.processor.core.RenderType
import javax.inject.Inject

class GetSkeletonsByContextUseCaseImpl @Inject constructor(
    private val database: AppDatabase
) : GetSkeletonsByContextUseCase {

    override suspend fun invoke(
        source: String
    ): List<RenderType> {
        val skeletonContext = database.skeletonsDao()
            .provideSkeletonsByContext(source)

        return skeletonContext?.renders ?: emptyList()
    }
}
