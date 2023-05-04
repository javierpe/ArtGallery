package com.javi.design.system.molecules

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.javi.design.system.atoms.ImageComponentView
import com.javi.design.system.atoms.StepperComponentView
import com.javi.design.system.extensions.withBounceClick
import com.javi.design.system.theme.DynamicListComposeTheme

const val BANNER_CAROUSEL_IMAGE_TEST_TAG = "banner-carousel-image"

@Suppress("LongParameterList")
@Composable
fun BannerImageView(
    modifier: Modifier = Modifier,
    quantity: Int = 0,
    imageURL: String,
    title: String,
    description: String,
    onAdd: () -> Unit,
    onDecrement: () -> Unit,
    onClickAction: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (card, stepper) = createRefs()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .withBounceClick()
                .clickable {
                    onClickAction()
                }
                .testTag(BANNER_CAROUSEL_IMAGE_TEST_TAG),
            shape = RoundedCornerShape(16.dp),
            elevation = 15.dp
        ) {
            ImageComponentView(
                modifier = Modifier.fillMaxSize(),
                imageURL = imageURL
            )

            title.takeIf { it.isNotEmpty() }?.let {
                BannerInfoView(
                    modifier = Modifier.fillMaxSize(),
                    title = it,
                    description = description
                )
            }
        }

        StepperComponentView(
            modifier = Modifier
                .constrainAs(stepper) {
                    top.linkTo(card.top, 16.dp)
                    end.linkTo(card.end, 16.dp)
                },
            quantity = quantity,
            onAdd = onAdd,
            onDecrement = onDecrement
        )
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
            description = "Description",
            onAdd = { },
            onDecrement = { }
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
            description = "Description",
            onAdd = { },
            onDecrement = { }
        ) { }
    }
}
