package com.nucu.dynamiclistcompose.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javi.data.enums.ContextType
import com.nucu.dynamiclistcompose.data.actions.ContextViewAction
import com.nucu.dynamiclistcompose.data.controllers.DefaultDynamicListController
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

abstract class ContextViewModel: ViewModel() {

    @Inject
    lateinit var headerAdapterController: DefaultDynamicListController

    @Inject
    lateinit var bodyAdapterController: DefaultDynamicListController

    private val _contextViewAction = MutableStateFlow<ContextViewAction?>(null)
    val contextViewAction: StateFlow<ContextViewAction?> = _contextViewAction.asStateFlow()

    abstract val context: ContextType

    /**
     * Maybe this can be removed and create abstract properties
     * like state or other params for request model.
     * The idea is only change properties and not all the model
      */
    abstract val requestModel: DynamicListRequestModel
}