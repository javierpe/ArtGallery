package com.javi.dynamic.list.presentation.ui.base

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.design.system.atoms.ErrorView
import com.javi.design.system.atoms.LoaderView
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.actions.ScrollAction
import com.javi.dynamic.list.data.actions.TargetAction
import com.javi.dynamic.list.data.actions.sendAction
import com.javi.dynamic.list.data.controllers.DynamicListComposeControllerImpl
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.presentation.viewModels.DynamicListViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Suppress("LongParameterList")
@Composable
fun ContextViewContent(
    dynamicListComposeController: DynamicListComposeControllerImpl? = null,
    showCaseState: ShowCaseState,
    bodyListState: LazyListState,
    requestModel: DynamicListRequestModel,
    destinationsNavigator: DestinationsNavigator? = null,
    dynamicListListener: (DynamicListState) -> Unit,
    forceReload: Boolean = false,
    dynamicListViewModel: DynamicListViewModel = hiltViewModel()
) {
    val uiState by dynamicListViewModel.dynamicListAction.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    LaunchedEffect(forceReload) {
        if (forceReload) {
            dynamicListViewModel.load(requestModel)
        }
    }

    when (uiState) {
        is DynamicListUIState.SkeletonAction -> {
            dynamicListListener.invoke(
                DynamicListState.OnStartLoading
            )
            dynamicListComposeController?.ComposeSkeletons(
                (uiState as DynamicListUIState.SkeletonAction).renderTypes
            )
        }

        is DynamicListUIState.LoadingAction -> {
            dynamicListListener.invoke(
                DynamicListState.OnStartLoading
            )
            Box(modifier = Modifier.fillMaxSize()) {
                LoaderView(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(120.dp)
                )
            }
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
            val successState = (uiState as DynamicListUIState.SuccessAction)
            DynamicListSuccess(
                action = successState,
                dynamicListComposeController = dynamicListComposeController,
                showCaseState = showCaseState,
                bodyListState = bodyListState,
                destinationsNavigator = destinationsNavigator,
                dynamicListListener = dynamicListListener
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
    destinationsNavigator: DestinationsNavigator? = null,
    dynamicListComposeController: DynamicListComposeControllerImpl? = null,
    showCaseState: ShowCaseState,
    bodyListState: LazyListState,
    dynamicListListener: (DynamicListState) -> Unit,
) {
    val actionBody = remember {
        mutableStateOf<ScrollAction?>(null)
    }

    val windowWidthSizeClass = calculateWindowSizeClass(activity = LocalContext.current as Activity)

    DynamicListLayout(
        widthSizeClass = windowWidthSizeClass.widthSizeClass,
        contentHeader = {
            dynamicListComposeController?.ComposeHeader(
                dynamicListObject = DynamicListObject(
                    widthSizeClass = windowWidthSizeClass.widthSizeClass,
                    destinationsNavigator = destinationsNavigator
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
            dynamicListComposeController?.ComposeBody(
                dynamicListObject = DynamicListObject(
                    widthSizeClass = windowWidthSizeClass.widthSizeClass,
                    destinationsNavigator = destinationsNavigator
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

    LaunchedEffect(action) {
        action.sendAction(dynamicListListener = dynamicListListener)
    }

    dynamicListComposeController?.dispatchShowCaseSequence(action.showCaseQueue)
}

@Composable
fun DynamicListLayout(
    widthSizeClass: WindowWidthSizeClass,
    contentHeader: @Composable () -> Unit,
    contentBody: @Composable () -> Unit
) {
    when (widthSizeClass) {
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
