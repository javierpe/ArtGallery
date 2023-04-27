package com.javi.dynamic.list.domain.useCases

import com.javi.basket.api.BasketUpdatesUseCase
import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.extensions.updateProducts
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
import kotlinx.coroutines.flow.combine
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
    private val getSkeletonsByContextUseCase: GetSkeletonsByContextUseCase,
    private val basketUpdatesUseCase: BasketUpdatesUseCase
) : GetDynamicListUseCase {

    override suspend operator fun invoke(
        page: Int,
        requestModel: DynamicListRequestModel,
        withSkeletons: Boolean
    ): Flow<DynamicListUIState> {
        return repository
            .get(page, requestModel)
            .combine(basketUpdatesUseCase.basketProducts) { data, basket ->
                if (basket.isNotEmpty() && data is DynamicListUIState.ResponseAction) {
                    data.updateProducts(basket)
                } else {
                    data
                }
            }
            .map {
                if (it is DynamicListUIState.ResponseAction) {
                    getDynamicListShowCaseUseCaseImpl(
                        header = it.header,
                        body = it.body
                    )
                } else {
                    it
                }
            }
            .onEach {
                if (it is DynamicListUIState.ResponseAction) {
                    saveSkeletonsUseCase(
                        body = it.body,
                        header = it.header,
                        source = requestModel.contextType.source
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
