package com.nucu.dynamiclistcompose.models

sealed class DynamicListAction {
    object LoadingAction : DynamicListAction()
    class ErrorAction(val message: String): DynamicListAction()
    class SuccessAction(val container: DynamicListContainer): DynamicListAction()
}