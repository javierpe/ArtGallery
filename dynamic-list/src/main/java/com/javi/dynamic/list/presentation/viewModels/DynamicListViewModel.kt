package com.javi.dynamic.list.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javi.dynamic.list.data.actions.DynamicListFlowState
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.domain.api.useCases.GetDynamicListUseCase
import com.javi.dynamic.list.presentation.ui.state.UIState
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

    private val _dynamicListAction = MutableStateFlow<UIState>(
        UIState.None
    )

    val dynamicListAction: StateFlow<UIState> = _dynamicListAction

    fun load(dynamicListRequestModel: DynamicListRequestModel) {
        job?.cancel()
        job = viewModelScope.launch {
            getDynamicListUseCase(0, dynamicListRequestModel)
                .collect { flowState ->
                    when (flowState) {
                        is DynamicListFlowState.WithoutSkeletonDataAction -> _dynamicListAction.emit(
                            UIState.LoadingState
                        )

                        is DynamicListFlowState.SkeletonDataAction -> _dynamicListAction.emit(
                            UIState.SkeletonState(
                                flowState.renderTypes
                            )
                        )

                        is DynamicListFlowState.ErrorAction -> _dynamicListAction.emit(
                            UIState.ErrorState(flowState.exception.message.orEmpty())
                        )

                        is DynamicListFlowState.SuccessAction -> _dynamicListAction.emit(
                            UIState.SuccessState(
                                header = flowState.header,
                                body = flowState.body,
                                showCaseQueue = flowState.showCaseQueue
                            )
                        )

                        else -> UIState.None
                    }
                }
        }
    }
}
