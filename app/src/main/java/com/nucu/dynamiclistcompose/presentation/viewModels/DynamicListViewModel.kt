package com.nucu.dynamiclistcompose.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javi.basket.api.LocalBasketApi
import com.nucu.dynamiclistcompose.data.actions.DynamicListAction
import com.nucu.dynamiclistcompose.data.api.DynamicListUseCaseApi
import com.nucu.dynamiclistcompose.data.extensions.propagateBasketProducts
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListViewModel @Inject constructor(
    private val useCase: DynamicListUseCaseApi,
    private val basketApi: LocalBasketApi
) : ViewModel() {

    private var job: Job? = null

    private val _dynamicListAction = MutableStateFlow<DynamicListAction>(DynamicListAction.LoadingAction)
    val dynamicListAction: StateFlow<DynamicListAction> = _dynamicListAction.asStateFlow()

    init {
        viewModelScope.launch {
            basketApi.setUp()
        }
    }

    fun load(requestModel: DynamicListRequestModel) {
        job?.cancel()
        job = viewModelScope.launch {
            useCase.get(0, requestModel)
                .combine(basketApi.basketProducts) { data, basket ->
                    if (basket.isNotEmpty() && data is DynamicListAction.SuccessAction) {
                        data.propagateBasketProducts(basket)
                    } else {
                        data
                    }
                }.collect {
                _dynamicListAction.value = it
            }
        }
    }
}