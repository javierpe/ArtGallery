package com.nucu.dynamiclistcompose.viewModels

import androidx.lifecycle.ViewModel
import com.nucu.dynamiclistcompose.actions.ContextViewAction
import com.nucu.dynamiclistcompose.adapters.DefaultAdapterController
import com.nucu.dynamiclistcompose.models.AnalyticSources
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

abstract class ContextViewModel: ViewModel() {

    @Inject
    lateinit var headerAdapterController: DefaultAdapterController

    @Inject
    lateinit var bodyAdapterController: DefaultAdapterController

    private val _contextViewAction = MutableStateFlow<ContextViewAction?>(null)
    val contextViewAction: StateFlow<ContextViewAction?> = _contextViewAction

    abstract val source: AnalyticSources

    /**
     * Maybe this can be removed and create abstract properties
     * like state or other params for request model
      */
    abstract val requestModel: DynamicListRequestModel

    fun reload() {
        _contextViewAction.value = ContextViewAction.Reload
    }
}