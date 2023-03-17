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
import com.javier.api.NavigationController
import com.nucu.dynamiclistcompose.destinations.ProductImageScreenDestination
import com.nucu.dynamiclistcompose.presentation.components.card.CardImage
import com.nucu.dynamiclistcompose.presentation.components.common.ImageComponentView
import com.nucu.dynamiclistcompose.presentation.ui.components.VerticalGrid

@Composable
fun CardsGridComponentViewScreen(
    images: List<CardImage>,
    navigationController: NavigationController
) {
    CardsGridComponentView(
        data = images,
    ) {
        navigationController.navigateTo(
            ProductImageScreenDestination(it)
        )
    }
}

@Composable
fun CardsGridComponentView(
    data: List<CardImage>,
    onClick: (String) -> Unit
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
                        onClick.invoke(item.imageURL)
                    },
                imageURL = item.imageURL
            )
        }
    }
}