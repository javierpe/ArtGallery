package com.nucu.dynamiclistcompose.bannerScreen.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.javier.api.models.Route
import com.nucu.dynamiclistcompose.bannerScreen.presentation.contents.ProductImageContent
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = "banner_screen/{${Route.BannerScreen.IMAGE_URL}}"
)
@Composable
fun ProductImageScreen(
    bannerImageURL: String,
) {
    ProductImageContent(
        imageURL = bannerImageURL,
    )
}

@Composable
@Preview
fun PreviewProductImageScreen() {
    ProductImageScreen(
        "Hello"
    )
}