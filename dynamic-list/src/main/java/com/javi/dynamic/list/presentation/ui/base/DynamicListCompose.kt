package com.javi.dynamic.list.presentation.ui.base

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.design.system.atoms.ErrorView
import com.javi.design.system.atoms.LoaderView
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.javi.dynamic.list.data.actions.ScrollAction
import com.javi.dynamic.list.data.actions.TargetAction
import com.javi.dynamic.list.data.controllers.DynamicListComposeControllerImpl
import com.javi.dynamic.list.data.models.DynamicListObject
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.presentation.ui.state.UIState
import com.javi.dynamic.list.presentation.ui.state.sendAction
import com.javi.dynamic.list.presentation.viewModels.DynamicListViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

const val HEADER_ID = "header"
const val BG_HEADER_ID = "bg-header"
const val BODY_ID = "body"
const val BG_BODY_ID = "bg-body"

@Suppress("LongParameterList")
@Composable
fun ContextViewContent(
    modifier: Modifier = Modifier,
    dynamicListComposeController: DynamicListComposeControllerImpl? = null,
    showCaseState: ShowCaseState,
    bodyListState: LazyListState,
    requestModel: DynamicListRequestModel,
    destinationsNavigator: DestinationsNavigator? = null,
    dynamicListListener: (DynamicListState) -> Unit,
    forceReload: Boolean = false,
    dynamicListViewModel: DynamicListViewModel = hiltViewModel()
) {
    val uiState by dynamicListViewModel.dynamicListAction.collectAsState()

    LaunchedEffect(forceReload) {
        if (forceReload) {
            dynamicListViewModel.load(requestModel)
        }
    }

    when (uiState) {
        is UIState.SkeletonState -> {
            dynamicListListener.invoke(
                DynamicListState.OnStartLoading
            )
            dynamicListComposeController?.ComposeSkeletons(
                (uiState as UIState.SkeletonState).renderTypes
            )
        }

        is UIState.LoadingState -> {
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

        is UIState.ErrorState -> {
            ErrorView(
                modifier = Modifier.fillMaxSize(),
                exception = (uiState as UIState.ErrorState).message
            ) {
                dynamicListViewModel.load(requestModel)
            }
        }

        is UIState.SuccessState -> {
            val successState = (uiState as UIState.SuccessState)
            DynamicListSuccess(
                modifier = modifier,
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
    modifier: Modifier = Modifier,
    action: UIState.SuccessState,
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

    val modifierHeaderUpdated = remember {
        derivedStateOf {
            when (windowWidthSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> Modifier.wrapContentSize()
                WindowWidthSizeClass.Medium -> Modifier.wrapContentWidth().fillMaxHeight().padding(16.dp)
                else -> Modifier.wrapContentWidth().fillMaxHeight()
            }
        }
    }

    val modifierBodyUpdated = remember {
        derivedStateOf {
            when (windowWidthSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> Modifier.wrapContentSize()
                WindowWidthSizeClass.Medium -> Modifier.wrapContentWidth().fillMaxHeight().padding(16.dp)
                else -> Modifier.wrapContentWidth().fillMaxHeight()
            }
        }
    }

    DynamicListLayout(
        modifier = modifier,
        widthSizeClass = windowWidthSizeClass.widthSizeClass,
        contentHeader = {
            dynamicListComposeController?.ComposeHeader(
                modifier = modifierHeaderUpdated.value.layoutId(HEADER_ID),
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
                modifier = modifierBodyUpdated.value.layoutId(BODY_ID),
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
