package com.nucu.dynamiclistcompose.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.nucu.dynamiclistcompose.ui.theme.Typography
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach

private const val MAX_ELEMENTS = 3

@OptIn(ExperimentalUnitApi::class)
@Composable
fun CardsComponentView(
    data: CardsModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        val decoration by remember {
            derivedStateOf {
                TextDecoration.Underline
            }
        }

        val letterSpacing by remember {
            derivedStateOf {
                TextUnit(40f, TextUnitType.Sp)
            }
        }

        val titleUpperCase by remember {
            derivedStateOf { data.title.uppercase() }
        }

        Text(
            text = titleUpperCase,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            style = Typography.h6,
            color = MaterialTheme.colors.secondary,
            letterSpacing = letterSpacing,
            textAlign = TextAlign.Center,
            textDecoration = decoration
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        ) {
            items(items = data.cardElements, key = { it.hashCode() }) {

                val pictures by remember {
                    derivedStateOf {
                        it.images.take(MAX_ELEMENTS)
                    }
                }

                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 5.dp
                ) {

                    Column(
                        modifier = Modifier
                            .clickable { }
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = it.title,
                            color = MaterialTheme.colors.secondary
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {

                            pictures.forEach { cardImage ->
                                AsyncImage(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(5.dp)),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(cardImage.imageURL)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                )
                            }

                            if (it.images.size > MAX_ELEMENTS) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .clip(RoundedCornerShape(16.dp))
                                ) {
                                    Text(
                                        text = "+${it.images.size - MAX_ELEMENTS}",
                                        style = Typography.button
                                    )
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
            ),
            title = "Title"
        )
    )
}