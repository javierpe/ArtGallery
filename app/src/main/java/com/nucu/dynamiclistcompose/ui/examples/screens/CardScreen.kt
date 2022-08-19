package com.nucu.dynamiclistcompose.ui.examples.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.ui.examples.contents.CardContent
import com.nucu.dynamiclistcompose.ui.examples.viewModels.CardScreenViewModel

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