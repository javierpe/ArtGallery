package com.javi.dynamic.list.domain.useCases

import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.useCases.GetSkeletonsByContextUseCase
import com.javi.dynamic.list.domain.database.AppDatabase
import javax.inject.Inject

class GetSkeletonsByContextUseCaseImpl @Inject constructor(
    private val database: AppDatabase
) : GetSkeletonsByContextUseCase {

    override suspend fun invoke(
        source: String
    ): DynamicListUIState {
        val skeletonContext = database.skeletonsDao()
            .provideSkeletonsByContext(source)

        return skeletonContext?.let {
            DynamicListUIState.SkeletonAction(it.renders)
        } ?: kotlin.run {
            DynamicListUIState.LoadingAction
        }
    }
}
