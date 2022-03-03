package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeAction
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.viewModels.DynamicListViewModel

class DynamicListCompose : DynamicListComposeAction() {

    private var bodyComposeController: DynamicListComposeController? = null
    private var headerComposeController: DynamicListComposeController? = null
    private var footerComposeController: DynamicListComposeController? = null

    private val errorViewState = mutableStateOf(false)
    private val showBodyLoadingState = mutableStateOf(false)
    private val dynamicListRequestModel = mutableStateOf<DynamicListRequestModel?>(null)

    override fun setRequestModel(requestModel: DynamicListRequestModel) {
        dynamicListRequestModel.value = requestModel
    }

    override fun removeErrorView() {
        errorViewState.value = false
    }

    override fun hideBodyLoading() {
        errorViewState.value = true
    }

    override fun showBodyLoading() {
        showBodyLoadingState.value = true
    }

    @Composable
    override fun <T: DynamicListComposeController> Load(
        bodyAdapterController: T,
        headerAdapterController: T?,
        footerAdapterController: T?,
    ) {
        this.bodyComposeController = bodyAdapterController
        this.headerComposeController = headerAdapterController
        this.footerComposeController = footerAdapterController

        DynamicListContent()
    }

    @Composable
    private fun DynamicListContent(
        dynamicListViewModel: DynamicListViewModel = hiltViewModel()
    ) {
        dynamicListRequestModel.value?.let { dynamicListViewModel.load(it) }

        val dynamicListState = dynamicListViewModel.dynamicListContainer.collectAsState()

        if (dynamicListState.value != null) {

            // Add data to controllers
            bodyComposeController?.dispatch(dynamicListState.value?.bodies.orEmpty())
            headerComposeController?.dispatch(dynamicListState.value?.headers.orEmpty())
            footerComposeController?.dispatch(dynamicListState.value?.footers.orEmpty())

            // Show
            Column {
                headerComposeController?.DynamicListComposeComponent(dynamicListRequestModel.value!!)
                bodyComposeController?.DynamicListComposeComponent(dynamicListRequestModel.value!!)
                footerComposeController?.DynamicListComposeComponent(dynamicListRequestModel.value!!)
            }
        }
    }
}