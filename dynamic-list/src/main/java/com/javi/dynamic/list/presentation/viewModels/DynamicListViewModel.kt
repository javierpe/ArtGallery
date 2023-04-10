package com.javi.dynamic.list.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javi.basket.api.LocalBasketApi
import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.extensions.propagateBasketProducts
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.useCases.GetDynamicListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListViewModel @Inject constructor(
    private val getDynamicListUseCase: GetDynamicListUseCase,
    private val basketApi: LocalBasketApi
) : ViewModel() {

    private var job: Job? = null

    private val _dynamicListAction = MutableStateFlow<DynamicListUIState>(
        DynamicListUIState.None
    )

    val dynamicListAction: StateFlow<DynamicListUIState> = _dynamicListAction

    init {
        viewModelScope.launch {
            basketApi.setUp()
        }
    }

    fun load(dynamicListRequestModel: DynamicListRequestModel) {
        job?.cancel()
        job = viewModelScope.launch {
            getDynamicListUseCase(0, dynamicListRequestModel)
                .combine(basketApi.basketProducts) { data, basket ->
                    if (basket.isNotEmpty() && data is DynamicListUIState.SuccessAction) {
                        data.propagateBasketProducts(basket)
                    } else {
                        data
                    }
                }.collect {
                    _dynamicListAction.emit(it)
                }
        }
    }
}
