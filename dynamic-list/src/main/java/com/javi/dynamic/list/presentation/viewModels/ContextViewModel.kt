package com.javi.dynamic.list.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.controllers.DefaultDynamicListController
import com.javi.dynamic.list.presentation.ui.base.DynamicListStateListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

abstract class ContextViewModel : ViewModel() {

    @Inject
    lateinit var headerAdapterController: DefaultDynamicListController

    @Inject
    lateinit var bodyAdapterController: DefaultDynamicListController

    private val _state = MutableStateFlow<HashMap<String, String>>(hashMapOf())
    internal val state: StateFlow<HashMap<String, String>> = _state

    private val _dynamicListStateListener = MutableStateFlow<DynamicListStateListener?>(null)
    val dynamicListStateListener: StateFlow<DynamicListStateListener?> = _dynamicListStateListener

    abstract val context: ContextType

    internal fun sendDynamicListState(dynamicListState: DynamicListStateListener) {
        _dynamicListStateListener.value = dynamicListState
    }

    fun reload() {
        _dynamicListStateListener.value = null
    }
}
