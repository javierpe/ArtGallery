package com.javi.dynamic.list.presentation.components.bannerCarousel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.javi.data.ProductImageModel
import com.javi.design.system.data.showCase.ShapeType
import com.javi.design.system.data.showCase.ShowCaseStrategy
import com.javi.design.system.molecules.BannerImageView
import com.javi.design.system.molecules.TooltipView
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.javi.design.system.molecules.showCase.ShowCaseStyle
import com.javi.design.system.molecules.showCase.asShowCaseTarget
import com.javi.dynamic.list.R
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.render.processor.core.RenderType

const val BANNER_CAROUSEL_IMAGE_SCREEN_TEST_TAG = "banner-carousel-image-screen"

@Suppress("LongParameterList")
@Composable
fun BannerCarouselComponentViewScreen(
    modifier: Modifier,
    images: List<BannerModel>,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onAdd: (ProductImageModel) -> Unit,
    onDecrement: (ProductImageModel) -> Unit,
    onProductDetail: (String) -> Unit
) {
    BannerCarouselComponentView(
        modifier = modifier
            .testTag(BANNER_CAROUSEL_IMAGE_SCREEN_TEST_TAG),
        images = images,
        componentIndex = componentIndex,
        showCaseState = showCaseState,
        onAdd = onAdd,
        onDecrement = onDecrement,
        onProductDetail = { onProductDetail(it) }
    )
}

@Suppress("LongParameterList")
@Composable
fun BannerCarouselComponentView(
    modifier: Modifier,
    images: List<BannerModel>,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onAdd: (ProductImageModel) -> Unit,
    onDecrement: (ProductImageModel) -> Unit,
    onProductDetail: (String) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(
            items = images,
            contentType = { _, model ->
                model.javaClass.name
            },
            key = { _, model ->
                model.product.id
            }
        ) { index, item ->

            val modifierBanner = if (index == 0) {
                Modifier.asShowCaseTarget(
                    index = componentIndex,
                    style = ShowCaseStyle.Default.copy(
                        shapeType = ShapeType.RECTANGLE,
                        cornerRadius = 16.dp,
                        withAnimation = false
                    ),
                    content = {
                        TooltipView(text = stringResource(id = R.string.tooltip_baner_carousel))
                    },
                    strategy = ShowCaseStrategy(firstToHappen = true),
                    key = RenderType.BANNER_CAROUSEL.value,
                    state = showCaseState
                )
            } else {
                Modifier
            }

            BannerImageView(
                modifier = modifierBanner.size(200.dp),
                imageURL = item.product.imageURL,
                onClickAction = { onProductDetail(item.product.imageURL) },
                quantity = item.product.quantity,
                title = item.bannerInfo?.title.orEmpty(),
                description = item.bannerInfo?.description.orEmpty(),
                onDecrement = { onDecrement(item.product) },
                onAdd = { onAdd(item.product) }
            )
        }
    }
}
