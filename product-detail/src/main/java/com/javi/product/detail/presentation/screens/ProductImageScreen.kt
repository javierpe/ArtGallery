package com.javi.product.detail.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javi.data.ProductImageModel
import com.javi.product.detail.presentation.contents.ProductImageContent
import com.javi.product.detail.presentation.screens.destinations.ProductImageScreenDestination
import com.javi.product.detail.viewModels.ProductImageScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
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