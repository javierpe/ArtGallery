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
import com.javi.design.system.ImageComponentView
import com.javi.design.system.molecules.VerticalGrid

@Composable
fun CardsGridComponentViewScreen(
    images: List<ProductImageModel>,
    onProductDetail: (String) -> Unit
) {
    CardsGridComponentView(
        data = images,
    ) {
        onProductDetail(it.imageURL)
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
        data.forEach { item ->
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
