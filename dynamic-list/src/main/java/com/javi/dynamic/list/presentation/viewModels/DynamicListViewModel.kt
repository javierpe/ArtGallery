package com.javi.dynamic.list.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.useCases.GetDynamicListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListViewModel @Inject constructor(
    private val getDynamicListUseCase: GetDynamicListUseCase
) : ViewModel() {

    private var job: Job? = null

    private val _dynamicListAction = MutableStateFlow<DynamicListFlowState>(
        DynamicListFlowState.None
    )

    val dynamicListAction: StateFlow<DynamicListFlowState> = _dynamicListAction

    fun load(dynamicListRequestModel: DynamicListRequestModel) {
        job?.cancel()
        job = viewModelScope.launch {
            getDynamicListUseCase(0, dynamicListRequestModel)
                .collect {
                    _dynamicListAction.emit(it)
                }
        }
    }
}
