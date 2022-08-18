package com.nucu.dynamiclistcompose.ui.examples.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.ui.examples.contents.BannerContent
import com.nucu.dynamiclistcompose.ui.examples.viewModels.BannerScreenViewModel

@Composable
fun BannerScreen(
    imageURL: String,
    viewModel: BannerScreenViewModel = hiltViewModel()
) {
    BannerContent(
        imageURL = imageURL,
    ) {
        viewModel.onBackPressed()
    }
}

@Composable
@Preview
fun PreviewBannerScreen() {
    BannerScreen(
        "Hello"
    )
}