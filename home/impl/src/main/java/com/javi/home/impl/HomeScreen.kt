package com.javi.home.impl

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.data.enums.ContextType
import com.javi.design.system.theme.HeaderDark
import com.javi.design.system.theme.HeaderLight
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.models.MotionLayoutProperties
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.dynamic.list.presentation.ui.base.rememberDynamicListRequestState
import com.javi.home.impl.presentation.constraintSetEnd
import com.javi.home.impl.presentation.constraintSetStart
import com.javi.home.impl.viewModels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val requestModel = rememberDynamicListRequestState {
        DynamicListRequestModel(
            contextType = ContextType.HOME
        )
    }

    ContextView(
        title = "Art Gallery",
        viewModel = viewModel,
        destinationsNavigator = destinationsNavigator,
        requestModel = requestModel.value,
        motionLayoutProperties = MotionLayoutProperties(
            constraintSetStart = constraintSetStart(HeaderDark),
            constraintSetEnd = constraintSetEnd(HeaderLight)
        )
    )
}
