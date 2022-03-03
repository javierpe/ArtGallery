package com.nucu.dynamiclistcompose.controllers

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel

abstract class DynamicListComposeAction {

    abstract fun setRequestModel(requestModel: DynamicListRequestModel)

    abstract fun removeErrorView()

    abstract fun hideBodyLoading()

    abstract fun showBodyLoading()

    @Composable
    abstract fun <T: DynamicListComposeController> Load(
        bodyAdapterController: T,
        headerAdapterController: T?,
        footerAdapterController: T?,
    )
}