package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeLoader
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListAction
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.components.ErrorView
import com.nucu.dynamiclistcompose.ui.components.LoaderView
import com.nucu.dynamiclistcompose.viewModels.DynamicListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DynamicListCompose(
    requestModel: DynamicListRequestModel
) : DynamicListComposeLoader() {

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

        val scrollComponentState = dynamicListViewModel.scrollState.collectAsState()
        
        val scrollListState = rememberLazyListState()

        if (scrollComponentState.value != RenderType.UNDEFINED) {
            LaunchedEffect(bodyComposeController?.getMapComponents()?.size) {
                val element = bodyComposeController?.getMapComponents()?.first {
                    it.render == scrollComponentState.value.value
                }

                bodyComposeController?.getMapComponents()?.indexOf(element)?.let {
                    scrollListState.animateScrollToItem(it)
                }
            }
        }

        when (dynamicListState.value) {
            is DynamicListAction.SkeletonAction -> {
                val skeletons = (dynamicListState.value as DynamicListAction.SkeletonAction).renderTypes
                bodyComposeController?.dispatchSkeletons(skeletons)
                Column {
                    bodyComposeController?.DynamicListSkeletons()
                }
            }
            is DynamicListAction.LoadingAction -> {
                LoaderView()
                dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }
            is DynamicListAction.ErrorAction -> ErrorView {
                dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }
            is DynamicListAction.SuccessAction -> {
                val container = (dynamicListState.value as DynamicListAction.SuccessAction).container
                // Add data to controllers.
                bodyComposeController?.dispatch(container.bodies)
                headerComposeController?.dispatch(container.headers)
                footerComposeController?.dispatch(container.footers)

                // Show elements.
                Column {
                    headerComposeController?.DynamicListComposeComponent(
                        dynamicListRequestModel.value!!,
                        dynamicListViewModel
                    )

                    LazyColumn(
                        state = scrollListState
                    ) {

                        item {
                            bodyComposeController?.DynamicListComposeComponent(
                                dynamicListRequestModel.value!!,
                                dynamicListViewModel
                            )
                        }
                    }

                    footerComposeController?.DynamicListComposeComponent(
                        dynamicListRequestModel.value!!,
                        dynamicListViewModel
                    )
                }
            }
        }
    }
}
