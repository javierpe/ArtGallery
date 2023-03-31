package com.javi.dynamic.list.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javi.basket.api.LocalBasketApi
import com.javi.dynamic.list.data.actions.DynamicListUIEvents
import com.javi.dynamic.list.data.api.DynamicListUseCaseApi
import com.javi.dynamic.list.data.extensions.propagateBasketProducts
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListViewModel @Inject constructor(
    private val useCase: DynamicListUseCaseApi,
    private val basketApi: LocalBasketApi
) : ViewModel() {

    private var job: Job? = null

    private val _dynamicListAction = MutableStateFlow<DynamicListUIEvents>(
        DynamicListUIEvents.None
    )

    val dynamicListAction: StateFlow<DynamicListUIEvents> = _dynamicListAction

    init {
        viewModelScope.launch {
            basketApi.setUp()
        }
    }

    fun load(dynamicListRequestModel: DynamicListRequestModel) {
        job?.cancel()
        job = viewModelScope.launch {
            useCase.get(0, dynamicListRequestModel)
                .combine(basketApi.basketProducts) { data, basket ->
                    if (basket.isNotEmpty() && data is DynamicListUIEvents.SuccessAction) {
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