package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nucu.dynamiclistcompose.controllers.DynamicListController
import com.nucu.dynamiclistcompose.models.DynamicListContainer
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicListViewModel @Inject constructor(
    private val controller: DynamicListController
) : ViewModel() {

    private val _dynamicListContainer = MutableStateFlow<DynamicListContainer?>(null)
    val dynamicListContainer: StateFlow<DynamicListContainer?> = _dynamicListContainer

    fun load(requestModel: DynamicListRequestModel) {
        viewModelScope.launch {
            _dynamicListContainer.value = getDynamicList(0, requestModel)
        }
    }

    private suspend fun getDynamicList(
        page: Int,
        requestModel: DynamicListRequestModel
    ): DynamicListContainer {
        return controller.get(
            page,
            requestModel,
        )
    }
}