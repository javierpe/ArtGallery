package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeLoader
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.models.DynamicListAction
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.ui.components.ErrorView
import com.nucu.dynamiclistcompose.ui.components.LoaderView
import com.nucu.dynamiclistcompose.viewModels.DynamicListViewModel

class DynamicListCompose(requestModel: DynamicListRequestModel) : DynamicListComposeLoader() {

    private var bodyComposeController: DynamicListComposeController? = null
    private var headerComposeController: DynamicListComposeController? = null
    private var footerComposeController: DynamicListComposeController? = null

    private val dynamicListRequestModel = mutableStateOf<DynamicListRequestModel?>(requestModel)

    @Composable
    override fun <T: DynamicListComposeController> DynamicListScreen(
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
        val dynamicListState = dynamicListViewModel.dynamicListAction.collectAsState()

        when (dynamicListState.value) {
            is DynamicListAction.LoadingAction -> {
                LoaderView()
                dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }
            is DynamicListAction.ErrorAction -> ErrorView {
                dynamicListViewModel.retry(dynamicListRequestModel.value!!)
            }
            is DynamicListAction.SuccessAction -> {
                val container = (dynamicListState.value as DynamicListAction.SuccessAction).container
                // Add data to controllers.
                bodyComposeController?.dispatch(container.bodies)
                headerComposeController?.dispatch(container.headers)
                footerComposeController?.dispatch(container.footers)

                // Show elements.
                Column {
                    headerComposeController?.DynamicListComposeComponent(dynamicListRequestModel.value!!)
                    bodyComposeController?.DynamicListComposeComponent(dynamicListRequestModel.value!!)
                    footerComposeController?.DynamicListComposeComponent(dynamicListRequestModel.value!!)
                }
            }
        }
    }
}
