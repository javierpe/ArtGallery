package com.javi.dynamic.list.domain.useCases

import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.repositories.DynamicListRepository
import com.javi.dynamic.list.data.useCases.GetDynamicListShowCaseUseCase
import com.javi.dynamic.list.data.useCases.GetDynamicListUseCase
import com.javi.dynamic.list.data.useCases.GetSkeletonsByContextUseCase
import com.javi.dynamic.list.data.useCases.SaveSkeletonsUseCase
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
    private val repository: DynamicListRepository,
    private val getDynamicListShowCaseUseCaseImpl: GetDynamicListShowCaseUseCase,
    private val saveSkeletonsUseCase: SaveSkeletonsUseCase,
    private val getSkeletonsByContextUseCase: GetSkeletonsByContextUseCase
) : GetDynamicListUseCase {

    override suspend operator fun invoke(
        page: Int,
        requestModel: DynamicListRequestModel,
        withSkeletons: Boolean
    ): Flow<DynamicListUIState> {
        return repository
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
                emit(DynamicListUIState.ErrorAction(it))
            }
            .flowOn(ioDispatcher)
    }
}
