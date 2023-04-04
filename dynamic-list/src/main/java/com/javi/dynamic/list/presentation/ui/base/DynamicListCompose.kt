package com.javi.dynamic.list.presentation.ui.base

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.dynamic.list.data.actions.ContextViewAction
import com.javi.dynamic.list.data.actions.DynamicListUIState
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
fun ContextViewContent(
    bodyAdapterController: DynamicListComposeController? = null,
    headerAdapterController: DynamicListComposeController? = null,
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

        is DynamicListUIState.SkeletonAction -> {
            dynamicListListener.invoke(
                DynamicListListener.OnStartLoading
            )
            bodyAdapterController?.dispatchSkeletons(
                (uiState as DynamicListUIState.SkeletonAction).renderTypes
            )
            bodyAdapterController?.DynamicListSkeletons()
        }

        is DynamicListUIState.LoadingAction -> {
            dynamicListListener.invoke(
                DynamicListListener.OnStartLoading
            )
            LoaderView()
        }

        is DynamicListUIState.ErrorAction -> {
            ErrorView(
                modifier = Modifier.fillMaxSize(),
                exception = (uiState as DynamicListUIState.ErrorAction).exception
            ) {
                dynamicListViewModel.load(requestModel)
            }
        }

        is DynamicListUIState.SuccessAction -> {
            DynamicListSuccess(
                action = (uiState as DynamicListUIState.SuccessAction),
                headerComposeController = headerAdapterController,
                bodyComposeController = bodyAdapterController,
                showCaseState = showCaseState,
                bodyListState = bodyListState
            )
        }

        else -> Unit
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Suppress("LongParameterList")
@Composable
fun DynamicListSuccess(
    action: DynamicListUIState.SuccessAction,
    headerComposeController: DynamicListComposeController? = null,
    bodyComposeController: DynamicListComposeController? = null,
    showCaseState: ShowCaseState,
    bodyListState: LazyListState
) {
    bodyComposeController?.dispatchShowCaseSequence(action.showCaseQueue)

    val actionBody = remember {
        mutableStateOf<ScrollAction?>(null)
    }

    val windowWidthSizeClass = calculateWindowSizeClass(activity = LocalContext.current as Activity)

    DynamicListLayout(
        widthSizeClass = windowWidthSizeClass.widthSizeClass,
        contentHeader = {
            headerComposeController?.ComposeHeader(
                dynamicListObject = DynamicListObject(
                    widthSizeClass = windowWidthSizeClass.widthSizeClass
                ),
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
                dynamicListObject = DynamicListObject(
                    widthSizeClass = windowWidthSizeClass.widthSizeClass
                ),
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
