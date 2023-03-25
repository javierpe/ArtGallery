package com.javi.design.system.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javi.design.system.ImageComponentView
import com.javi.design.system.theme.Typography

const val BANNER_CAROUSEL_IMAGE_TEST_TAG = "banner-carousel-image"

@Composable
fun BannerImageView(
    modifier: Modifier = Modifier,
    quantity: Int = 0,
    imageURL: String,
    title: String,
    description: String,
    onClickAction: () -> Unit
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .testTag(BANNER_CAROUSEL_IMAGE_TEST_TAG)
    ) {

        ImageComponentView(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClickAction()
                },
            imageURL = imageURL
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
                    .align(Alignment.TopEnd)
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