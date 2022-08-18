package com.nucu.dynamiclistcompose.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.nucu.dynamiclistcompose.ui.theme.Typography

private const val MAX_ELEMENTS = 2

@Composable
fun CardsComponentView(
    data: CardsModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "Cards",
            modifier = Modifier.padding(start = 16.dp),
            style = Typography.h6
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        ) {
            items(items = data.cardElements, key = { it.hashCode() }) {
                Card(
                    modifier = Modifier
                        .wrapContentSize(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 5.dp
                ) {

                    Column(
                        modifier = Modifier
                            .clickable {  }
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = it.title)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            it.images.take(MAX_ELEMENTS).forEach { cardImage ->
                                SubcomposeAsyncImage(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(5.dp)),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(cardImage.imageURL)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                ) {
                                    when (painter.state) {
                                        is AsyncImagePainter.State.Loading -> {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        is AsyncImagePainter.State.Error -> {

                                        }
                                        else -> {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                }
                            }

                            if (data.cardElements.size > MAX_ELEMENTS) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .clip(RoundedCornerShape(16.dp))
                                ) {
                                    Text(text = "+${data.cardElements.size - MAX_ELEMENTS}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCardsComponentView() {
    CardsComponentView(
        data = CardsModel(
            cardElements = listOf(
                CardElement("Hola", images = emptyList())
            )
        )
    )
}