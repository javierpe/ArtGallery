package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nucu.dynamiclistcompose.actions.DynamicListAction
import com.nucu.dynamiclistcompose.api.DynamicListUseCaseApi
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListViewModel @Inject constructor(
    private val useCase: DynamicListUseCaseApi
) : ViewModel() {

    private var job: Job? = null

    private val _dynamicListAction = MutableStateFlow<DynamicListAction>(DynamicListAction.LoadingAction)
    val dynamicListAction: StateFlow<DynamicListAction> = _dynamicListAction

    fun load(requestModel: DynamicListRequestModel) {
        job?.cancel()
        job = viewModelScope.launch {
             getDynamicList(0, requestModel).collect {
                 _dynamicListAction.value = it
             }
        }
    }

    private suspend fun getDynamicList(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListAction> {
        return useCase.get(
            page,
            requestModel,
        )
    }
}