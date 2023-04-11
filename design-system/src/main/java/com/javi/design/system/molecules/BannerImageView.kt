package com.javi.design.system.molecules

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import com.javi.design.system.atoms.ImageComponentView
import com.javi.design.system.extensions.withBounceClick
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.design.system.theme.Typography
import kotlinx.coroutines.launch

const val BANNER_CAROUSEL_IMAGE_TEST_TAG = "banner-carousel-image"

@Suppress("LongParameterList")
@Composable
fun BannerImageView(
    modifier: Modifier = Modifier,
    quantity: Int = 0,
    imageURL: String,
    title: String,
    description: String,
    onClickAction: () -> Unit
) {
    val lightVibrantColor = remember {
        mutableStateOf(Color.Black)
    }

    val scope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .withBounceClick()
            .shadow(
                clip = false,
                ambientColor = lightVibrantColor.value,
                spotColor = lightVibrantColor.value,
                elevation = 15.dp,
            )
            .testTag(BANNER_CAROUSEL_IMAGE_TEST_TAG),
        shape = RoundedCornerShape(16.dp),
        elevation = 15.dp
    ) {
        ImageComponentView(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClickAction()
                },
            imageURL = imageURL,
            onState = {
                if (it is AsyncImagePainter.State.Success) {
                    scope.launch {
                        Palette.Builder(it.result.drawable.toBitmap()).generate { palette ->
                            palette?.let { paletteResult ->
                                paletteResult.vibrantSwatch?.let { swatch ->
                                    lightVibrantColor.value = Color(swatch.rgb)
                                }
                            }
                        }
                    }
                }
            }
        )

        title.takeIf { it.isNotEmpty() }?.let {
            BannerInfoView(
                modifier = Modifier.fillMaxSize(),
                title = it,
                description = description
            )
        }

        if (quantity > 0) {
            Text(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(bottomStart = 10.dp))
                    .background(Color.White),
                text = quantity.toString(),
                style = Typography.h6,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(
    heightDp = 90,
    widthDp = 200,
    name = "BannerImageView | Night Mode On",
    uiMode = UI_MODE_NIGHT_YES
)
fun PreviewNightModeBannerImageView() {
    DynamicListComposeTheme {
        BannerImageView(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp)),
            imageURL = "",
            title = "Title",
            description = "Description"
        ) { }
    }
}

@Composable
@Preview(
    heightDp = 90,
    widthDp = 200,
    name = "BannerImageView | Night Mode Off",
    uiMode = UI_MODE_NIGHT_NO
)
fun PreviewNoNightModeBannerImageView() {
    DynamicListComposeTheme {
        BannerImageView(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp)),
            imageURL = "",
            title = "Title",
            description = "Description"
        ) { }
    }
}
