package com.nucu.dynamiclistcompose.presentation.components.cardsGrid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.design.system.ImageComponentView
import com.javi.data.ProductImageModel
import com.javi.design.system.molecules.VerticalGrid
import com.javi.product.detail.presentation.screens.destinations.ProductImageScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun CardsGridComponentViewScreen(
    images: List<ProductImageModel>,
    navigator: DestinationsNavigator
) {
    CardsGridComponentView(
        data = images,
    ) {
        navigator.navigate(
            ProductImageScreenDestination(it)
        )
    }
}

@Composable
fun CardsGridComponentView(
    data: List<ProductImageModel>,
    onClick: (ProductImageModel) -> Unit
) {

    VerticalGrid(
        modifier = Modifier
            .testTag("card-container")
    ) {
        data.forEach {item ->
            ImageComponentView(
                modifier = Modifier
                    .height(250.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        onClick.invoke(item)
                    },
                imageURL = item.imageURL
            )
        }
    }
}