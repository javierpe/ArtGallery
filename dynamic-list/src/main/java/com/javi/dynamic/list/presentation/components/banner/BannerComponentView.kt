package com.javi.dynamic.list.presentation.components.banner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.data.ProductImageModel
import com.javi.design.system.data.showCase.ShapeType
import com.javi.design.system.data.showCase.ShowCaseStrategy
import com.javi.design.system.molecules.BannerImageView
import com.javi.design.system.molecules.TooltipView
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.javi.design.system.molecules.showCase.ShowCaseStyle
import com.javi.design.system.molecules.showCase.asShowCaseTarget
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.dynamic.list.R
import com.javi.render.processor.core.RenderType

const val BANNER_IMAGE_TEST_TAG = "banner-image"
const val BANNER_IMAGE_SCREEN_TEST_TAG = "banner-image-screen"

@Suppress("FunctionNaming", "LongParameterList")
@Composable
fun BannerComponentViewScreen(
    modifier: Modifier,
    model: BannerModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onAdd: (ProductImageModel) -> Unit,
    onDecrement: (ProductImageModel) -> Unit,
    onProductDetail: (String) -> Unit
) {
    BannerComponentView(
        modifier = modifier.testTag(BANNER_IMAGE_SCREEN_TEST_TAG),
        componentIndex = componentIndex,
        showCaseState = showCaseState,
        imageUrl = model.product.imageURL,
        quantity = model.product.quantity,
        title = model.bannerInfo?.title.orEmpty(),
        description = model.bannerInfo?.description.orEmpty(),
        onAdd = {
            onAdd(model.product)
        },
        onDecrement = {
            onDecrement(model.product)
        }
    ) {
        onProductDetail(model.product.imageURL)
    }
}

@Suppress("LongParameterList")
@Composable
fun BannerComponentView(
    modifier: Modifier,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    imageUrl: String,
    quantity: Int,
    title: String,
    description: String,
    onAdd: () -> Unit,
    onDecrement: () -> Unit,
    onClickAction: () -> Unit
) {
    BannerImageView(
        modifier = modifier
            .height(150.dp)
            .fillMaxSize()
            .testTag(BANNER_IMAGE_TEST_TAG)
            .padding(start = 16.dp, end = 16.dp)
            .asShowCaseTarget(
                index = componentIndex,
                style = ShowCaseStyle.Default.copy(
                    shapeType = ShapeType.RECTANGLE,
                    cornerRadius = 16.dp,
                    withAnimation = false
                ),
                content = {
                    TooltipView(text = stringResource(R.string.tooltip_banner))
                },
                strategy = ShowCaseStrategy(firstToHappen = true),
                key = RenderType.BANNER.value,
                state = showCaseState
            ),
        imageURL = imageUrl,
        onClickAction = onClickAction,
        quantity = quantity,
        title = title,
        description = description,
        onAdd = onAdd,
        onDecrement = onDecrement
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewBannerComponentView() {
    val model = BannerModel(ProductImageModel(0, 0, ""))
    DynamicListComposeTheme {
        BannerComponentView(
            modifier = Modifier,
            imageUrl = model.product.imageURL,
            quantity = model.product.quantity,
            title = model.bannerInfo?.title.orEmpty(),
            description = model.bannerInfo?.description.orEmpty(),
            componentIndex = 0,
            showCaseState = rememberShowCaseState(),
            onAdd = {},
            onDecrement = {},
            onClickAction = {}
        )
    }
}
