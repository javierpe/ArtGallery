package com.nucu.dynamiclistcompose.useCases

import com.nucu.dynamiclistcompose.controllers.DynamicListController
import com.nucu.dynamiclistcompose.actions.DynamicListAction
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class DynamicListUseCase @Inject constructor(
    private val controller: DynamicListController
) {
    suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListAction> {
        return controller.get(page, requestModel).catch {
            emit(DynamicListAction.ErrorAction(it))
        }
    }
}