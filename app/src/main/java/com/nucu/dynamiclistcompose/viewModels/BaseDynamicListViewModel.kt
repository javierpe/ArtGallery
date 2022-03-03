package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeAction
import com.nucu.dynamiclistcompose.listeners.DynamicListComposeLoader
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseDynamicListViewModel @Inject constructor(
    private val composeLoader: DynamicListComposeLoader
) : ViewModel() {

    fun load(requestModel: DynamicListRequestModel): DynamicListComposeAction {
        return composeLoader.getAction(requestModel)
    }
}