package com.javi.dynamic.list.domain.useCases

import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.useCases.GetSkeletonsByContextUseCase
import com.javi.dynamic.list.di.IODispatcher
import com.javi.dynamic.list.domain.database.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSkeletonsByContextUseCaseImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
    private val database: AppDatabase
): GetSkeletonsByContextUseCase {

    override suspend fun invoke(
        source: String
    ): DynamicListUIState {
        val skeletonContext = withContext(ioDispatcher) {
            database.skeletonsDao()
                .provideSkeletonsByContext(source)
        }

        return skeletonContext?.let {
            DynamicListUIState.SkeletonAction(it.renders)
        } ?: kotlin.run {
            DynamicListUIState.LoadingAction
        }
    }
}