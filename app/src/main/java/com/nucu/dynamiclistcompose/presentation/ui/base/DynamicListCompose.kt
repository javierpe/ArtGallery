package com.nucu.dynamiclistcompose.presentation.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nucu.dynamiclistcompose.data.actions.ContextViewAction
import com.nucu.dynamiclistcompose.data.actions.DynamicListAction
import com.nucu.dynamiclistcompose.data.actions.ScrollAction
import com.nucu.dynamiclistcompose.data.actions.TargetAction
import com.nucu.dynamiclistcompose.data.controllers.DynamicListComposeController
import com.nucu.dynamiclistcompose.data.controllers.DynamicListComposeLoader
import com.nucu.dynamiclistcompose.data.models.DynamicListObject
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import com.javi.design.system.atoms.ErrorView
import com.javi.design.system.atoms.LoaderView
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.presentation.viewModels.DynamicListViewModel

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
        dynamicListObject: DynamicListObject,
        showCaseState: ShowCaseState,
        bodyListState: LazyListState
    ) {
        this.bodyComposeController = bodyAdapterController
        this.headerComposeController = headerAdapterController

        DynamicListContent(
            action = action,
            dynamicListObject = dynamicListObject,
            showCaseState = showCaseState,
            bodyListState = bodyListState,
        )
    }

    @Composable
    private fun DynamicListContent(
        dynamicListObject: DynamicListObject,
        action: ContextViewAction?,
        showCaseState: ShowCaseState,
        bodyListState: LazyListState,
        dynamicListViewModel: DynamicListViewModel = hiltViewModel()
    ) {
        val dynamicListState by dynamicListViewModel.dynamicListAction.collectAsStateWithLifecycle()

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

            is DynamicListAction.ErrorAction ->
                ErrorView((dynamicListState as DynamicListAction.ErrorAction).exception) {
                dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }

            is DynamicListAction.SuccessAction -> {
                val dynamicListAction = (dynamicListState as DynamicListAction.SuccessAction)
                bodyComposeController?.dispatchShowCaseSequence(dynamicListAction.showCaseQueue)

                val actionBody = remember {
                    mutableStateOf<ScrollAction?>(null)
                }

                DynamicListView(
                    dynamicListObject = dynamicListObject,
                    contentHeader = {
                        headerComposeController?.ComposeHeader(
                            dynamicListObject = dynamicListObject,
                            showCaseState = showCaseState,
                            elements = dynamicListAction.header
                        ) {
                            if (it.target == TargetAction.BODY) {
                                actionBody.value = it
                            }
                        }
                    },
                    contentBody = {
                        bodyComposeController?.ComposeBody(
                            dynamicListObject = dynamicListObject,
                            sharedAction = actionBody.value,
                            showCaseState = showCaseState,
                            bodyListState = bodyListState,
                            elements = dynamicListAction.body
                        ) {
                            if (it.target == TargetAction.BODY) {
                                actionBody.value = it
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DynamicListView(
    dynamicListObject: DynamicListObject,
    contentHeader: @Composable () -> Unit,
    contentBody: @Composable () -> Unit
) {
    when(dynamicListObject.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column {
                contentHeader()
                contentBody()
            }
        }

        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            Row {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    contentHeader()
                }

                Box(
                    modifier = Modifier.weight(1.5f)
                ) {
                    contentBody()
                }
            }
        }
    }
}
