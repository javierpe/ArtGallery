package com.javi.card.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.card.page.viewModels.CardsPageViewModel
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.dynamic.list.presentation.ui.base.rememberDynamicListRequestState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun CardsPage(
    id: Int = 0,
    title: String = "",
    widthSizeClass: WindowWidthSizeClass,
    viewModel: CardsPageViewModel = hiltViewModel()
) {
    val dynamicListState = rememberDynamicListRequestState {
        viewModel.requestModel.copy(
            state = hashMapOf("id" to id.toString())
        )
    }

    Column {
        Button(onClick = {
            dynamicListState.requestModel.value = viewModel.requestModel.copy(
                state = hashMapOf("id" to "2")
            )
        }) {
            Text(text = "Update state")
        }

        ContextView(
            title = title,
            widthSizeClass = widthSizeClass,
            viewModel = viewModel,
            dynamicListRequestModel = dynamicListState.requestModel.value
        )
    }
}