package com.javi.product.detail.impl.presentation.screens

import android.content.Intent
import androidx.compose.runtime.Composable
import com.javi.data.DEEPLINK_MAIN
import com.javi.product.detail.impl.presentation.contents.ProductImageContent
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination(
    deepLinks = [
        DeepLink(
            uriPattern = "${DEEPLINK_MAIN}product_image_screen/{imageUrl}/",
            action = Intent.ACTION_VIEW
        )
    ],

)
@Composable
fun ProductImageScreen(
    imageURL: String? = null,
    destinationsNavigator: DestinationsNavigator
) {
    ProductImageContent(
        imageURL = imageURL.orEmpty(),
        destinationsNavigator = destinationsNavigator,
    )
}
