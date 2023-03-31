package com.javi.product.detail.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.data.DEEPLINK_MAIN
import com.javi.product.detail.presentation.contents.ProductImageContent
import com.javi.product.detail.viewModels.ProductImageScreenViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination(
    deepLinks = [
        DeepLink(
            uriPattern = "${DEEPLINK_MAIN}product_detail/{imageUrl}"
        )
    ]
)
@Composable
fun ProductImageScreen(
    imageUrl: String = "",
    viewModel: ProductImageScreenViewModel = hiltViewModel()
) {
    val productQuantityUpdates = viewModel.productQuantityUpdates.collectAsStateWithLifecycle()

    ProductImageContent(
        imageURL = imageUrl,
        quantity = productQuantityUpdates.value,
        onAdd = {
            //viewModel.onAdd(productImageModel)
        },
        onDecrement = {
            //viewModel.onDecrement(productImageModel)
        },
        onBack = {
            viewModel.onBackPressed()
        }
    )
}