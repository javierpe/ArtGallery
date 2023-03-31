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
import com.javi.dynamic.list.data.actions.DynamicListUIEvents
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
    dynamicListListener: (DynamicListListener) -> Unit
) {
    DynamicListContent(
        bodyAdapterController = bodyAdapterController,
        headerAdapterController = headerAdapterController,
        action = action,
        dynamicListObject = dynamicListObject,
        showCaseState = showCaseState,
        bodyListState = bodyListState,
        dynamicListListener = dynamicListListener,
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
    requestModel: DynamicListRequestModel,
    dynamicListListener: (DynamicListListener) -> Unit,
    dynamicListViewModel: DynamicListViewModel = hiltViewModel()
) {

    val uiState by dynamicListViewModel.dynamicListAction.collectAsStateWithLifecycle()

    LaunchedEffect(requestModel) {
        dynamicListViewModel.load(requestModel)
    }

    LaunchedEffect(action) {
        when (action) {
            is ContextViewAction.Reload -> dynamicListViewModel.load(requestModel)
            else -> Unit
        }
    }

    when (uiState) {

        is DynamicListUIEvents.SkeletonAction -> {
            dynamicListListener.invoke(
                DynamicListListener.OnStartLoading
            )
            bodyAdapterController?.dispatchSkeletons(
                (uiState as DynamicListUIEvents.SkeletonAction).renderTypes
            )
            bodyAdapterController?.DynamicListSkeletons()
        }

        is DynamicListUIEvents.LoadingAction -> {
            dynamicListListener.invoke(
                DynamicListListener.OnStartLoading
            )
            LoaderView()
        }

        is DynamicListUIEvents.ErrorAction -> {
            ErrorView((uiState as DynamicListUIEvents.ErrorAction).exception) {
                dynamicListViewModel.load(requestModel)
            }
        }

        is DynamicListUIEvents.SuccessAction -> {
            DynamicListSuccess(
                action = (uiState as DynamicListUIEvents.SuccessAction),
                headerComposeController = headerAdapterController,
                bodyComposeController = bodyAdapterController,
                dynamicListObject = dynamicListObject,
                showCaseState = showCaseState,
                bodyListState = bodyListState
            )
        }

        else -> Unit
    }
}

@Suppress("LongParameterList")
@Composable
fun DynamicListSuccess(
    action: DynamicListUIEvents.SuccessAction,
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
