package com.nucu.dynamiclistcompose.bannerScreen.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nucu.dynamiclistcompose.bannerScreen.presentation.contents.BannerContent

@Composable
fun BannerScreen(
    imageURL: String,
) {
    BannerContent(
        imageURL = imageURL,
    )
}

@Composable
@Preview
fun PreviewBannerScreen() {
    BannerScreen(
        "Hello"
    )
}