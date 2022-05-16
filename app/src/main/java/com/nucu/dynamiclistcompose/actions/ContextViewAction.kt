package com.nucu.dynamiclistcompose.actions

sealed class ContextViewAction {

    /**
     * This reload current context view.
     */
    object Reload: ContextViewAction()
}
