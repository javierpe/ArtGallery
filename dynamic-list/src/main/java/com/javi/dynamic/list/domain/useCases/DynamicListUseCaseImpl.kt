package com.javi.dynamic.list.domain.useCases

import com.javi.basket.api.BasketUpdatesUseCase
import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.extensions.updateProducts
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.repositories.DynamicListRepository
import com.javi.dynamic.list.data.useCases.GetDynamicListUseCase
import com.javi.dynamic.list.data.useCases.GetSkeletonsByContextUseCase
import com.javi.dynamic.list.data.useCases.GetTooltipSequenceUseCase
import com.javi.dynamic.list.data.useCases.SaveSkeletonsUseCase
import com.javi.dynamic.list.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class DynamicListUseCaseImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
    private val repository: DynamicListRepository,
    private val getTooltipSequenceUseCase: GetTooltipSequenceUseCase,
    private val saveSkeletonsUseCase: SaveSkeletonsUseCase,
    private val getSkeletonsByContextUseCase: GetSkeletonsByContextUseCase,
    private val basketUpdatesUseCase: BasketUpdatesUseCase
) : GetDynamicListUseCase {

    override suspend operator fun invoke(
        page: Int,
        requestModel: DynamicListRequestModel,
    ): Flow<DynamicListFlowState> {
        return repository
            .get(page, requestModel)
            .combine(basketUpdatesUseCase.basketProducts) { data, basket ->
                if (basket.isNotEmpty() && data is DynamicListFlowState.ResponseAction) {
                    data.updateProducts(basket)
                } else {
                    data
                }
            }
            .map {
                if (it is DynamicListFlowState.ResponseAction) {
                    val showCaseResultModel = getTooltipSequenceUseCase(
                        header = it.header,
                        body = it.body
                    )

                    DynamicListFlowState.SuccessAction(
                        body = showCaseResultModel.body,
                        header = showCaseResultModel.header,
                        showCaseQueue = showCaseResultModel.showCaseQueue
                    )
                } else {
                    it
                }
            }
            .onEach {
                if (it is DynamicListFlowState.SuccessAction) {
                    saveSkeletonsUseCase(
                        body = it.body.map { component -> component.componentItemModel },
                        header = it.header.map { component -> component.componentItemModel },
                        context = requestModel.contextType.source
                    )
                }
            }
            .onStart {
                val skeletons = getSkeletonsByContextUseCase(requestModel.contextType.source)
                emit(
                    if (skeletons.isEmpty()) {
                        DynamicListFlowState.WithoutSkeletonDataAction
                    } else {
                        DynamicListFlowState.SkeletonDataAction(skeletons)
                    }
                )
            }
            .catch {
                emit(DynamicListFlowState.ErrorAction(it))
            }
            .flowOn(ioDispatcher)
    }
}
