package com.nucu.dynamiclistcompose.cardScreen.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.cardScreen.presentation.contents.CardContent
import com.nucu.dynamiclistcompose.cardScreen.presentation.viewModels.CardScreenViewModel

@Composable
fun CardScreen(
    title: String,
    images: List<String>,
    viewModel: CardScreenViewModel = hiltViewModel()
) {
    CardContent(title = title, images = images) {
        viewModel.goToBack()
    }
}