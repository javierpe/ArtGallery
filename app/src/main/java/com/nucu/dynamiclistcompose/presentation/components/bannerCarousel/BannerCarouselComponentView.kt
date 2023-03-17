package com.nucu.dynamiclistcompose.presentation.components.bannerCarousel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.models.showCase.ShapeType
import com.nucu.dynamiclistcompose.data.models.showCase.ShowCaseStrategy
import com.javi.render.processor.data.enums.RenderType
import com.javier.api.NavigationController
import com.nucu.dynamiclistcompose.destinations.ProductImageScreenDestination
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.nucu.dynamiclistcompose.presentation.components.common.BannerImageView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.asShowCaseTarget

const val BANNER_CAROUSEL_IMAGE_TEST_TAG = "banner-carousel-image"
const val BANNER_CAROUSEL_IMAGE_SCREEN_TEST_TAG = "banner-carousel-image-screen"

@Suppress("LongParameterList")
@Composable
fun BannerCarouselComponentViewScreen(
    modifier: Modifier,
    images: List<BannerModel>,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    widthSizeClass: WindowWidthSizeClass,
    navigationController: NavigationController
) {
    BannerCarouselComponentView(
        modifier = modifier
            .testTag(BANNER_CAROUSEL_IMAGE_SCREEN_TEST_TAG),
        images = images,
        componentIndex = componentIndex,
        showCaseState = showCaseState,
        isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded,
    ) {
        navigationController.navigateTo(
            ProductImageScreenDestination(it)
        )
    }
}

@Suppress("LongParameterList")
@Composable
fun BannerCarouselComponentView(
    modifier: Modifier,
    isExpandedScreen: Boolean = false,
    images: List<BannerModel>,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onClickAction: (String) -> Unit,
) {

    val height = if (isExpandedScreen) {
        200.dp
    } else 300.dp

    val width = if (isExpandedScreen) {
        250.dp
    } else 350.dp

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
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
            } else Modifier

            BannerImageView(
                modifier = modifierBanner
                    .height(height)
                    .width(width),
                imageURL = item.product.imageURL,
                onClickAction = {
                    onClickAction(item.product.imageURL)
                },
                bannerInfo = item.bannerInfo
            )
        }
    }
}