package com.nucu.dynamiclistcompose.cardScreen.presentation.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.presentation.ui.base.ContextView
import com.nucu.dynamiclistcompose.presentation.viewModels.CardsViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CardScreen(
    id: Int,
    title: String,
    widthSizeClass: WindowWidthSizeClass,
    viewModel: CardsViewModel = hiltViewModel()
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