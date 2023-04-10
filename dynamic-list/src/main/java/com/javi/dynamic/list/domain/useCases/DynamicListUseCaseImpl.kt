package com.javi.dynamic.list.domain.useCases

import android.util.Log
import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.repositories.DynamicListRepository
import com.javi.dynamic.list.data.useCases.GetDynamicListUseCase
import com.javi.dynamic.list.data.useCases.GetSkeletonsByContextUseCase
import com.javi.dynamic.list.data.useCases.SaveSkeletonsUseCase
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class DynamicListUseCaseImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
    private val controller: DynamicListRepository,
    private val getDynamicListShowCaseUseCaseImpl: GetDynamicListShowCaseUseCaseImpl,
    private val saveSkeletonsUseCase: SaveSkeletonsUseCase,
    private val getSkeletonsByContextUseCase: GetSkeletonsByContextUseCase
): GetDynamicListUseCase {

    override suspend operator fun invoke(
        page: Int,
        requestModel: DynamicListRequestModel,
        withSkeletons: Boolean
    ): Flow<DynamicListUIState> {
        return controller
            .get(page, requestModel)
            .map {
                if (it is DynamicListUIState.SuccessAction) {
                    getDynamicListShowCaseUseCaseImpl(it.container)
                } else {
                    it
                }
            }
            .onEach {
                if (it is DynamicListUIState.SuccessAction) {
                    saveSkeletonsUseCase(
                        it.container.body,
                        it.container.header,
                        requestModel.contextType.source
                    )
                }
            }
            .onStart {
                if (withSkeletons) {
                    emit(getSkeletonsByContextUseCase(requestModel.contextType.source))
                }
            }
            .catch {
                Log.e("DL_ERROR", it.message.toString())
                emit(DynamicListUIState.ErrorAction(it))
            }
            .flowOn(ioDispatcher)
    }
}