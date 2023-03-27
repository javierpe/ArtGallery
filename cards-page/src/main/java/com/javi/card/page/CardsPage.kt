package com.javi.card.page

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.card.page.viewModels.CardsPageViewModel
import com.javi.dynamic.list.presentation.ui.base.ContextView
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
    val state = remember {
        hashMapOf("id" to id.toString())
    }

    ContextView(
        title = title,
        widthSizeClass = widthSizeClass,
        viewModel = viewModel,
        state = state
    )
}