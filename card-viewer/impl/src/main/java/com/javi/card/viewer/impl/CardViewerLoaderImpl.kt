package com.javi.card.viewer.impl

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.javi.card.viewer.api.CardViewerLoader
import com.javi.dynamic.list.presentation.ui.base.ContextView
import com.javi.card.viewer.impl.viewModels.CardsViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import javax.inject.Inject

class CardViewerLoaderImpl @Inject constructor(

): CardViewerLoader {

    @Composable
    fun CardScreen(
        widthSizeClass: WindowWidthSizeClass,
        navigator: DestinationsNavigator
    ) {
        /*CardContent(
            id,
            title,
            widthSizeClass,
            navigator
        )*/
    }

    @Composable fun CardContent(
        id: Int,
        title: String,
        widthSizeClass: WindowWidthSizeClass,
        navigator: DestinationsNavigator,
        viewModel: CardsViewModel = hiltViewModel()
    ) {
        val state = remember {
            hashMapOf("id" to id.toString())
        }

        ContextView(
            title = title,
            widthSizeClass = widthSizeClass,
            viewModel = viewModel,
            state = state,
            navigator = navigator
        )
    }
}