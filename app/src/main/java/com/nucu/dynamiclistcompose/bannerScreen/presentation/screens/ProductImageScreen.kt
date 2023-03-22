package com.nucu.dynamiclistcompose.bannerScreen.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.api.data.ProductImageModel
import com.nucu.dynamiclistcompose.bannerScreen.presentation.contents.ProductImageContent
import com.nucu.dynamiclistcompose.presentation.viewModels.ProductImageScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    navArgsDelegate = ProductImageModel::class
)
@Composable
fun ProductImageScreen(
    navigator: DestinationsNavigator,
    productImageModel: ProductImageModel,
    viewModel: ProductImageScreenViewModel = hiltViewModel()
) {

    val productQuantityUpdates = viewModel.productQuantityUpdates.collectAsStateWithLifecycle()

    LaunchedEffect( productImageModel) {
        viewModel.setProduct(productImageModel)
    }

    ProductImageContent(
        imageURL = productImageModel.imageURL,
        navigator = navigator,
        quantity = productQuantityUpdates.value,
        onAdd = {
            viewModel.onAdd(productImageModel)
        },
        onDecrement = {
            viewModel.onDecrement(productImageModel)
        }
    )
}
