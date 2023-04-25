package com.javi.dynamic.list.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javi.data.enums.ContextType
import com.javi.dynamic.list.data.controllers.DefaultDynamicListComposeController
import com.javi.dynamic.list.presentation.ui.base.DynamicListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

abstract class ContextViewModel : ViewModel() {

    /**
     * Body Dynamic List Controller
     */
    @Inject
    lateinit var dynamicListComposeController: DefaultDynamicListComposeController

    private val _dynamicListStateListener = MutableStateFlow<DynamicListState>(
        DynamicListState.None
    )

    /**
     * Collect this to listen Dynamic List States.
     */
    val dynamicListStateListener: StateFlow<DynamicListState> = _dynamicListStateListener

    /**
     * Context type of current screen.
     */
    abstract val context: ContextType

    /**
     * Allows update Dynamic List states.
     */
    internal fun sendDynamicListState(dynamicListState: DynamicListState) {
        _dynamicListStateListener.value = dynamicListState
    }

    /**
     * Forces Dynamic List reload.
     */
    fun reload() {
        _dynamicListStateListener.value = DynamicListState.None
    }
}
