package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.dynamic.list.data.actions.ContextViewAction
import com.javi.dynamic.list.data.actions.DynamicListAction
import com.javi.dynamic.list.data.actions.ScrollAction
import com.javi.dynamic.list.data.actions.TargetAction
import com.javi.design.system.atoms.ErrorView
import com.javi.design.system.atoms.LoaderView
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.javi.dynamic.list.data.controllers.DynamicListComposeController
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.presentation.viewModels.DynamicListViewModel

@Suppress("LongParameterList")
@Composable
fun <T: DynamicListComposeController> DynamicListScreen(
    bodyAdapterController: T,
    headerAdapterController: T,
    action: ContextViewAction?,
    dynamicListObject: DynamicListObject,
    showCaseState: ShowCaseState,
    bodyListState: LazyListState,
    requestModel: DynamicListRequestModel,
    dynamicListState: (DynamicListStateAction) -> Unit
) {

    DynamicListContent(
        bodyAdapterController = bodyAdapterController,
        headerAdapterController = headerAdapterController,
        action = action,
        dynamicListObject = dynamicListObject,
        showCaseState = showCaseState,
        bodyListState = bodyListState,
        dynamicListState = dynamicListState,
        requestModel = requestModel
    )
}

@Suppress("LongParameterList")
@Composable
private fun DynamicListContent(
    bodyAdapterController: DynamicListComposeController? = null,
    headerAdapterController: DynamicListComposeController? = null,
    dynamicListObject: DynamicListObject,
    action: ContextViewAction?,
    showCaseState: ShowCaseState,
    bodyListState: LazyListState,
    dynamicListState: (DynamicListStateAction) -> Unit,
    requestModel: DynamicListRequestModel,
    dynamicListViewModel: DynamicListViewModel = hiltViewModel()
) {

    val uiState by dynamicListViewModel.dynamicListAction.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = requestModel) {
        dynamicListViewModel.load(requestModel)
    }

    action?.let {
        when (it) {
            is ContextViewAction.Reload -> dynamicListViewModel.load(requestModel)
            else -> Unit
        }
    }

    when (uiState) {

        is DynamicListAction.SkeletonAction -> {
            bodyAdapterController?.dispatchSkeletons(
                (uiState as DynamicListAction.SkeletonAction).renderTypes
            )
            bodyAdapterController?.DynamicListSkeletons()
        }

        is DynamicListAction.LoadingAction -> {
            LoaderView()
        }

        is DynamicListAction.ErrorAction -> {
            ErrorView((uiState as DynamicListAction.ErrorAction).exception) {
                dynamicListViewModel.load(requestModel)
            }
        }

        is DynamicListAction.SuccessAction -> {
            DynamicListSuccess(
                action = (uiState as DynamicListAction.SuccessAction),
                headerComposeController = headerAdapterController,
                bodyComposeController = bodyAdapterController,
                dynamicListObject = dynamicListObject,
                showCaseState = showCaseState,
                bodyListState = bodyListState
            )
        }
    }
}

@Suppress("LongParameterList")
@Composable
fun DynamicListSuccess(
    action: DynamicListAction.SuccessAction,
    headerComposeController: DynamicListComposeController? = null,
    bodyComposeController: DynamicListComposeController? = null,
    dynamicListObject: DynamicListObject,
    showCaseState: ShowCaseState,
    bodyListState: LazyListState
) {
    bodyComposeController?.dispatchShowCaseSequence(action.showCaseQueue)

    val actionBody = remember {
        mutableStateOf<ScrollAction?>(null)
    }

    DynamicListLayout(
        widthSizeClass = dynamicListObject.widthSizeClass,
        contentHeader = {
            headerComposeController?.ComposeHeader(
                dynamicListObject = dynamicListObject,
                showCaseState = showCaseState,
                elements = action.header
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
                elements = action.body
            ) {
                if (it.target == TargetAction.BODY) {
                    actionBody.value = it
                }
            }
        }
    )
}

@Composable
fun DynamicListLayout(
    widthSizeClass: WindowWidthSizeClass,
    contentHeader: @Composable () -> Unit,
    contentBody: @Composable () -> Unit
) {
    when(widthSizeClass) {
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
