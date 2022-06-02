package com.nucu.dynamiclistcompose.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.actions.ContextViewAction
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeLoader
import com.nucu.dynamiclistcompose.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.actions.DynamicListAction
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.ui.components.ErrorView
import com.nucu.dynamiclistcompose.ui.components.LoaderView
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseScope
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.viewModels.DynamicListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DynamicListCompose(
    requestModel: DynamicListRequestModel
) : DynamicListComposeLoader() {

    private var bodyComposeController: DynamicListComposeController? = null
    private var headerComposeController: DynamicListComposeController? = null

    private val dynamicListRequestModel = mutableStateOf<DynamicListRequestModel?>(requestModel)

    @Composable
    override fun <T: DynamicListComposeController> DynamicListScreen(
        bodyAdapterController: T,
        headerAdapterController: T,
        action: ContextViewAction?,
        widthSizeClass: WindowWidthSizeClass,
        showCaseState: ShowCaseState
    ) {
        this.bodyComposeController = bodyAdapterController
        this.headerComposeController = headerAdapterController

        DynamicListContent(
            action = action,
            widthSizeClass = widthSizeClass,
            showCaseState = showCaseState
        )
    }

    @Composable
    private fun DynamicListContent(
        widthSizeClass: WindowWidthSizeClass,
        action: ContextViewAction?,
        showCaseState: ShowCaseState,
        dynamicListViewModel: DynamicListViewModel = hiltViewModel()
    ) {
        val dynamicListState by dynamicListViewModel.dynamicListAction.collectAsState()

        action?.let {
            when (it) {
                is ContextViewAction.Reload -> dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }
        }

        when (dynamicListState) {

            is DynamicListAction.SkeletonAction -> {
                val skeletons = (dynamicListState as DynamicListAction.SkeletonAction).renderTypes
                bodyComposeController?.dispatchSkeletons(skeletons)
                bodyComposeController?.DynamicListSkeletons()
            }

            is DynamicListAction.LoadingAction -> {
                LoaderView()
                dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }

            is DynamicListAction.ErrorAction -> ErrorView((dynamicListState as DynamicListAction.ErrorAction).exception) {
                dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }

            is DynamicListAction.SuccessAction -> {
                val container = (dynamicListState as DynamicListAction.SuccessAction).container
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(container) {
                    coroutineScope.launch {
                        headerComposeController?.dispatch(container.headers)
                        bodyComposeController?.dispatch(container.bodies)
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {

                    val actionBody = remember {
                        mutableStateOf<ScrollAction?>(null)
                    }

                    headerComposeController?.ComposeHeader(
                        widthSizeClass = widthSizeClass,
                        showCaseState = showCaseState
                    ) {
                        if (it.target == TargetAction.BODY) {
                            actionBody.value = it
                        }
                    }

                    bodyComposeController?.ComposeBody(
                        widthSizeClass = widthSizeClass,
                        sharedAction = actionBody.value,
                        showCaseState = showCaseState
                    ) {
                        if (it.target == TargetAction.BODY) {
                            actionBody.value = it
                        }
                    }
                }
            }
        }
    }
}
