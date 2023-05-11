package com.javi.dynamic.list.presentation.components.cardsGrid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.data.ProductImageModel
import com.javi.design.system.atoms.ImageComponentView
import com.javi.design.system.molecules.VerticalGrid

const val CARD_CONTAINER_TAG = "card-container"

@Composable
fun CardsGridComponentViewScreen(
    modifier: Modifier = Modifier,
    images: List<ProductImageModel>,
    onProductDetail: (String) -> Unit
) {
    CardsGridComponentView(
        modifier = modifier,
        data = images,
    ) {
        onProductDetail(it)
    }
}

@Composable
fun CardsGridComponentView(
    modifier: Modifier = Modifier,
    data: List<ProductImageModel>,
    onClick: (String) -> Unit
) {
    VerticalGrid(
        modifier = modifier
            .testTag(CARD_CONTAINER_TAG)
    ) {
        data.forEach { item ->
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
