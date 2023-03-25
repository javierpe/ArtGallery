package com.javi.dynamic.list.data.actions

sealed class ContextViewAction {

    /**
     * This reload current context view.
     */
    object Reload: ContextViewAction()
}
