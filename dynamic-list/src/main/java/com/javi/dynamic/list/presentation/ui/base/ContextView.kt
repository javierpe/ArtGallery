package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.constraintlayout.compose.MotionLayoutScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.design.system.molecules.headers.DynamicListHeaderComponentView
import com.javi.design.system.molecules.motionLayout.CollapsibleHeaderComponentView
import com.javi.design.system.molecules.showCase.ShowCase
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.dynamic.list.data.extensions.isMacroBenchmarkEnabled
import com.javi.dynamic.list.data.models.CONTEXT_VIEW_LAYOUT_ID
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.models.MotionLayoutProperties
import com.javi.dynamic.list.presentation.viewModels.ContextViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ContextView(
    title: String,
    destinationsNavigator: DestinationsNavigator? = null,
    requestModel: DynamicListRequestModel,
    motionLayoutProperties: MotionLayoutProperties? = null,
    viewModel: ContextViewModel
) {
    val state by viewModel.dynamicListStateListener.collectAsStateWithLifecycle()

    val showCaseState = rememberShowCaseState()

    val bodyListState = rememberLazyListState()

    ShowCase(
        showIntroShowCase = !isMacroBenchmarkEnabled(),
        state = showCaseState
    ) {
        MakeContextView(
            motionLayoutProperties = motionLayoutProperties,
            bodyListState = bodyListState,
            header = { scope ->
                DynamicListHeaderComponentView(
                    title = title,
                    contextType = viewModel.context,
                    destinationsNavigator = destinationsNavigator,
                    motionLayoutScope = scope
                )
            }
        ) {
            ContextViewContent(
                dynamicListComposeController = viewModel.dynamicListComposeController,
                showCaseState = showCaseState,
                bodyListState = bodyListState,
                requestModel = requestModel,
                destinationsNavigator = destinationsNavigator,
                forceReload = state == DynamicListState.None,
                dynamicListListener = {
                    viewModel.sendDynamicListState(it)
                }
            )
        }
    }
}

@Composable
fun MakeContextView(
    motionLayoutProperties: MotionLayoutProperties? = null,
    bodyListState: LazyListState,
    header: @Composable ((MotionLayoutScope?) -> Unit),
    contextView: @Composable ((PaddingValues?) -> Unit)
) {
    if (motionLayoutProperties == null) {
        Scaffold(
            topBar = {
                header(null)
            }
        ) { padding ->
            contextView(padding)
        }
    } else {
        CollapsibleHeaderComponentView(
            constraintSetStart = motionLayoutProperties.constraintSetStart,
            constraintSetEnd = motionLayoutProperties.constraintSetEnd,
            bodyListState = bodyListState,
            contentHeader = { motionLayoutScope ->
                header(motionLayoutScope)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .layoutId(CONTEXT_VIEW_LAYOUT_ID)
            ) {
                contextView(null)
            }
        }
    }
}
