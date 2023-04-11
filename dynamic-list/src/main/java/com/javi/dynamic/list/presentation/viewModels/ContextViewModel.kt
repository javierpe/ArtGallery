package com.javi.dynamic.list.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.controllers.DefaultDynamicListController
import com.javi.dynamic.list.presentation.ui.base.DynamicListStateListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

abstract class ContextViewModel : ViewModel() {

    /**
     * Header Dynamic List Controller
     */
    @Inject
    lateinit var headerAdapterController: DefaultDynamicListController

    /**
     * Body Dynamic List Controller
     */
    @Inject
    lateinit var bodyAdapterController: DefaultDynamicListController

    private val _dynamicListStateListener = MutableStateFlow<DynamicListStateListener?>(null)

    /**
     * Collect this to listen Dynamic List States.
     */
    val dynamicListStateListener: StateFlow<DynamicListStateListener?> = _dynamicListStateListener

    /**
     * Context type of current screen.
     */
    abstract val context: ContextType

    /**
     * Allows update Dynamic List states.
     */
    internal fun sendDynamicListState(dynamicListState: DynamicListStateListener) {
        _dynamicListStateListener.value = dynamicListState
    }

    /**
     * Forces Dynamic List reload.
     */
    fun reload() {
        _dynamicListStateListener.value = null
    }
}
