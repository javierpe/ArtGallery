package com.javi.design.system.molecules

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.data.ProductImageModel
import com.javi.design.system.extensions.withBounceClick
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.design.system.theme.Typography

private const val MAX_ELEMENTS = 3

@Composable
fun CardItemView(
    modifier: Modifier = Modifier,
    title: String,
    images: List<ProductImageModel>,
    onClick: () -> Unit
) {
    val pictures = remember {
        derivedStateOf {
            images.take(MAX_ELEMENTS)
        }
    }

    val number = remember {
        derivedStateOf { images.size - MAX_ELEMENTS }
    }

    Card(
        modifier = modifier
            .testTag("card-item")
            .wrapContentWidth()
            .height(100.dp)
            .withBounceClick(),
        shape = RoundedCornerShape(16.dp),
        elevation = 15.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {

        Column(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.secondary,
                style = Typography.button
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                pictures.value.forEach { cardImage ->
                    CardImageItem(imageURL = cardImage.imageURL)
                }

                if (images.size > MAX_ELEMENTS) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            text = "+${number.value}",
                            style = Typography.button
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(
    name = "CardItemView | Night Mode Off",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
fun PreviewNightModeCardItemView() {
    DynamicListComposeTheme {
        CardItemView(title = "Title", images = emptyList()) { }
    }
}

@Composable
@Preview(
    name = "CardItemView | Night Mode On",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun PreviewNoNightModeCardItemView() {
    DynamicListComposeTheme {
        CardItemView(title = "Title", images = emptyList()) { }
    }
}