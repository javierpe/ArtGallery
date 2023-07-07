package com.javi.product.detail.impl.presentation.contents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.javi.design.system.atoms.ImageComponentView
import com.javi.design.system.theme.DynamicListComposeTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ProductImageContent(
    imageURL: String,
    destinationsNavigator: DestinationsNavigator? = null
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = false

    Box {
        ImageComponentView(
            modifier = Modifier.fillMaxSize(),
            imageURL = imageURL,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview
fun PreviewBannerContent() {
    DynamicListComposeTheme {
        ProductImageContent(
            imageURL = "Hello"
        )
    }
}
