package com.javi.product.detail.impl.presentation.screens

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.data.DEEPLINK_MAIN
import com.javi.product.detail.impl.presentation.contents.ProductImageContent
import com.javi.product.detail.impl.viewModels.ProductImageScreenViewModel
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
    ]
)
@Composable
fun ProductImageScreen(
    imageUrl: String = "",
    destinationsNavigator: DestinationsNavigator,
    viewModel: ProductImageScreenViewModel = hiltViewModel()
) {
    val productQuantityUpdates = viewModel.productQuantityUpdates.collectAsStateWithLifecycle()

    ProductImageContent(
        imageURL = imageUrl,
        quantity = productQuantityUpdates.value,
        destinationsNavigator = destinationsNavigator,
        onDecrement = {
            //viewModel.onDecrement(productImageModel)
        }
    )
}