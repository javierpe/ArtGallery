package com.nucu.dynamiclistcompose.models

sealed class DynamicListAction {
    object LoadingAction : DynamicListAction()
    class ErrorAction(val exception: Exception): DynamicListAction()
    class SuccessAction(val container: DynamicListContainer): DynamicListAction()
}