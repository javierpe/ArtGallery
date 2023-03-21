package com.nucu.dynamiclistcompose.bannerScreen.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
    ProductImageContent(
        imageURL = productImageModel.imageURL,
    ) {
        viewModel.addToCart(productImageModel)
        navigator.popBackStack()
    }
}
