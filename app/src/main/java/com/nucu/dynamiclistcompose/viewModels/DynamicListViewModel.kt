package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nucu.dynamiclistcompose.controllers.DynamicListController
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListAction
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
import com.nucu.dynamiclistcompose.models.DynamicListContainer
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import dagger.Component
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListViewModel @Inject constructor(
    private val controller: DynamicListController
) : ViewModel(), DynamicListComponentAction {

    private val _dynamicListAction = MutableStateFlow<DynamicListAction>(DynamicListAction.LoadingAction)
    val dynamicListAction: StateFlow<DynamicListAction> = _dynamicListAction

    private val _scrollState = MutableStateFlow<RenderType>(RenderType.UNDEFINED)
    val scrollState: StateFlow<RenderType> = _scrollState

    fun load(requestModel: DynamicListRequestModel) {
        viewModelScope.launch {
             getDynamicList(0, requestModel).collect {
                 _dynamicListAction.value = it
             }
        }
    }

    private suspend fun getDynamicList(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListAction> {

        return controller.get(
            page,
            requestModel,
        )
    }

    // TODO: Implement queue
    override fun moveToFirstRender(renderType: RenderType) {
        if (scrollState.value != renderType) {
            _scrollState.value = renderType
        }
    }

    override fun addToolTip(message: String) {

    }
}