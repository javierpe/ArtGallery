package com.nucu.dynamiclistcompose.data.actions

sealed class ContextViewAction {

    /**
     * This reload current context view.
     */
    object Reload: ContextViewAction()
}
